package ru.realityfamily.party_server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.realityfamily.party_server.DB.DAO.PartyDAO;
import ru.realityfamily.party_server.DB.DAO.PersonDAO;
import ru.realityfamily.party_server.Models.Party;
import ru.realityfamily.party_server.Models.Person;
import ru.realityfamily.party_server.Models.PersonCredentials;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("party")
public class PartyController {

    private final PartyDAO partyDAO;
    private final PersonDAO personDAO;

    public PartyController(PartyDAO partyDAO, PersonDAO personDAO) {
        this.partyDAO = partyDAO;
        this.personDAO = personDAO;
    }

    @GetMapping("/all")
    ResponseEntity<List<Party>> getAllParties(Authentication authentication) {
        PersonCredentials.Role role = PersonCredentials.Role.valueOf(authentication.getAuthorities().iterator().next().getAuthority());

        List<Party> result = partyDAO.findAll().stream().filter(party -> {
            switch (role) {
                case ADMIN:
                    return true;
                case MODER:
                    return party.getStatus().equals(Party.Status.UNVERIFIED);
                case USER:
                    return party.getStatus().equals(Party.Status.VERIFIED);
                default:
                    return false;
            }
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/byId")
    ResponseEntity<Party> getPartyById(@RequestParam UUID id) {
        return ResponseEntity.ok(
                partyDAO.findByIdLike(id)
        );
    }

    @PostMapping("/add")
    ResponseEntity<Party> addNewPartyToList(@RequestBody Party party) {
        partyDAO.save(party);
        return ResponseEntity.ok(party);
    }

    @PutMapping("/verify")
    ResponseEntity<Party> verifyPartyById(@RequestParam UUID id, @RequestParam boolean verified, Authentication authentication) {
        PersonCredentials.Role role = PersonCredentials.Role.valueOf(authentication.getAuthorities().iterator().next().getAuthority());
        Party party = partyDAO.findByIdLike(id);
        if (party != null && !role.equals(PersonCredentials.Role.USER)) {
            party.setStatus(verified ? Party.Status.VERIFIED : Party.Status.CLOSED);
            partyDAO.save(party);
            return ResponseEntity.ok(party);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/accept")
    ResponseEntity<Party> acceptPersonOnParty(@RequestParam UUID id, Authentication authentication) {
        PersonCredentials.Role role = PersonCredentials.Role.valueOf(authentication.getAuthorities().iterator().next().getAuthority());
        Person person = personDAO.findByUserName(authentication.getName());
        Party party = partyDAO.findByIdLike(id);
        if (party != null && role.equals(PersonCredentials.Role.USER) && person != null) {
            party.getPeopleList().add(person);
            partyDAO.save(party);
            return ResponseEntity.ok(party);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/share")
    ResponseEntity<String> getLinkToShare(@RequestParam UUID id) {
        if (partyDAO.existsById(id)) {
            return ResponseEntity.ok(String.format("http://rf.party_app/%s", id.toString()));
        }
        return ResponseEntity.badRequest().build();
    }
}
