package ru.realityfamily.party_server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.realityfamily.party_server.DB.DAO.PartyDAO;
import ru.realityfamily.party_server.Models.Party;

import java.util.*;

@Controller
@RequestMapping("party")
public class PartyController {

    @Autowired
    private PartyDAO partyDAO;

    @GetMapping("/all")
    ResponseEntity<List<Party>> getAllParties() {
        return ResponseEntity.ok(partyDAO.findAll());
    }

    @GetMapping("/byId")
    ResponseEntity<Party> getPartyById(@RequestParam UUID id) {
        return ResponseEntity.ok(
                partyDAO.findByIdLike(id)
        );
    }

    @PostMapping("/add")
    ResponseEntity<Party> addNewPartyToList(@RequestBody Party party){
        partyDAO.save(party);
        return ResponseEntity.ok(party);
    }

    @PutMapping("/verify")
    ResponseEntity<Party> verifyPartyById(@RequestParam UUID id) {
        Party party = partyDAO.findByIdLike(id);
        if (party != null) {
            party.setStatus(Party.Status.VERIFIED);
            partyDAO.save(party);
            return ResponseEntity.ok(party);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/share")
    ResponseEntity<String> getLinkToShare(@RequestParam UUID id) {
        return ResponseEntity.ok(String.format("http://rf.party_app/%s", id.toString()));
    }
}
