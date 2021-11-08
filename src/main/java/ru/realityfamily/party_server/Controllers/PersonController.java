package ru.realityfamily.party_server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.realityfamily.party_server.DB.DAO.PersonDAO;
import ru.realityfamily.party_server.Models.Person;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("person")
public class PersonController {
    @Autowired
    PersonDAO personDAO;

    @GetMapping("/byId")
    ResponseEntity<Person> getInfoById(@RequestParam UUID id) {
        Person person = personDAO.findByIdLike(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
