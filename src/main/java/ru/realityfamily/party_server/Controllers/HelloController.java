package ru.realityfamily.party_server.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HelloController {

    @GetMapping("/")
    ResponseEntity<String> greetingInIndex(Authentication authentication){
        return ResponseEntity.ok(String.format("Greetings to %s from Party Server!",
                authentication.getAuthorities().iterator().next().getAuthority()));
    }
}
