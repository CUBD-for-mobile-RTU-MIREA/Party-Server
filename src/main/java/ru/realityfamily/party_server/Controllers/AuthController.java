package ru.realityfamily.party_server.Controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.realityfamily.party_server.DB.DAO.PersonCredentialsDAO;
import ru.realityfamily.party_server.DB.DAO.PersonDAO;
import ru.realityfamily.party_server.Models.Person;
import ru.realityfamily.party_server.Models.PersonCredentials;
import ru.realityfamily.party_server.Security.Services.JWTUtils;
import ru.realityfamily.party_server.Security.Services.MyUserDetailsService;

import java.util.Map;

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
    public ResponseEntity<String> authentication(@RequestBody AuthRequest body) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.login,
                            body.password
                    )
            );
        } catch (BadCredentialsException e) {
            LoggerFactory.getLogger(AuthController.class).warn("Incorrect username or password");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(body.login);

        if (userDetails != null) {
            final String token = jwtUtils.generateToken(userDetails);

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> registration(@RequestBody RegisterRequest body) throws Exception {
        if (body.login != null && body.password!= null && body.role != null
                && !personCredentialsDAO.existsByLoginLike(body.login)) {
            Person person = new Person();
            person = personDAO.save(person);

            personCredentialsDAO.save(
                    new PersonCredentials(
                            body.login,
                            passwordEncoder.encode(body.password),
                            body.role,
                            person
                    )
            );
        } else {
            return ResponseEntity.badRequest().build();
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.login,
                            body.password
                    )
            );
        } catch (BadCredentialsException e) {
            LoggerFactory.getLogger(AuthController.class).warn("Incorrect username or password");
            return ResponseEntity.badRequest().build();
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(body.login);

        if (userDetails != null) {
            final String token = jwtUtils.generateToken(userDetails);

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public static final class AuthRequest{
        public String login;
        public String password;
    }

    public static final class RegisterRequest{
        public String login;
        public String password;
        public PersonCredentials.Role role;
    }
}
