package ru.realityfamily.party_server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.realityfamily.party_server.Controllers.POJO.PersonPOJO;
import ru.realityfamily.party_server.DB.DAO.PersonDAO;
import ru.realityfamily.party_server.Models.Person;
import ru.realityfamily.party_server.Models.PersonCredentials;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("person")
public class PersonController {
    private final PersonDAO personDAO;

    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/byId")
    ResponseEntity<PersonPOJO> getInfoById(@RequestParam UUID id) {
        Person person = personDAO.findByIdLike(id);
        if (person != null) {
            return ResponseEntity.ok(new PersonPOJO(person));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/byToken")
    ResponseEntity<PersonPOJO> getInfoByToken(Authentication authentication) {
        Person person = personDAO.findByUserName(authentication.getName());
        if (person != null) {
            return ResponseEntity.ok(new PersonPOJO(person));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
