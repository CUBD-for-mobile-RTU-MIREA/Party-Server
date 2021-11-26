package ru.realityfamily.party_server.Controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.realityfamily.party_server.DB.DAO.PersonCredentialsDAO;
import ru.realityfamily.party_server.DB.DAO.PersonDAO;
import ru.realityfamily.party_server.Models.Person;
import ru.realityfamily.party_server.Models.PersonCredentials;
import ru.realityfamily.party_server.Security.Services.JWTUtils;
import ru.realityfamily.party_server.Security.Services.MyUserDetailsService;

import java.util.Map;
import java.util.UUID;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PersonCredentialsDAO personCredentialsDAO;
    @Autowired
    private PersonDAO personDAO;

    @PostMapping("auth")
    public ResponseEntity<String> authentication(@RequestBody PersonCredentials body) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.getLogin(),
                            body.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            LoggerFactory.getLogger(AuthController.class).warn("Incorrect username or password");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(body.getLogin());

        if (userDetails != null) {
            final String token = jwtUtils.generateToken(userDetails);

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> registration(@RequestBody PersonCredentials body) throws Exception {
        if (body.getLogin() != null && body.getPassword()!= null
                && !personCredentialsDAO.existsByLoginLike(body.getLogin()) && body.getPerson() != null) {
            Person person = personDAO.save(body.getPerson());

            personCredentialsDAO.save(
                    new PersonCredentials(
                            body.getLogin(),
                            passwordEncoder.encode(body.getPassword()),
                            PersonCredentials.Role.USER,
                            person
                    )
            );
        } else {
            return ResponseEntity.badRequest().build();
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.getLogin(),
                            body.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            LoggerFactory.getLogger(AuthController.class).warn("Incorrect username or password");
            return ResponseEntity.badRequest().build();
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(body.getLogin());

        if (userDetails != null) {
            final String token = jwtUtils.generateToken(userDetails);

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/auth/oauth")
    ResponseEntity<OutAuthResponse> outAuth(@RequestParam OAUTH2Provider provider) {
        return ResponseEntity.ok(
                new OutAuthResponse(
                        "path",
                        UUID.randomUUID()
                )
        );
    }

    public static final class OutAuthResponse{
        public String path;
        public UUID session_id;

        public OutAuthResponse(String path, UUID session_id) {
            this.path = path;
            this.session_id = session_id;
        }
    }

    public enum OAUTH2Provider{
        VK,
        Google,
        Meta
    }
}
