package ru.realityfamily.party_server.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    ResponseEntity<String> greetingInIndex(Authentication authentication){
        return ResponseEntity.ok(String.format("Greetings to %s from Party Server!",
                authentication.getAuthorities().iterator().next().getAuthority()));
    }
}
