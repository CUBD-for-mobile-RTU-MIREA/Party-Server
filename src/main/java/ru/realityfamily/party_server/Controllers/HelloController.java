package ru.realityfamily.party_server.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    ResponseEntity<String> greetingInIndex(){
        return ResponseEntity.ok("Greetings from Party Server!");
    }
}
