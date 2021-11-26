package ru.realityfamily.party_server.DB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.realityfamily.party_server.DB.DAO.PartyDAO;
import ru.realityfamily.party_server.DB.DAO.PersonCredentialsDAO;
import ru.realityfamily.party_server.DB.DAO.PersonDAO;
import ru.realityfamily.party_server.Models.Party;
import ru.realityfamily.party_server.Models.Person;
import ru.realityfamily.party_server.Models.PersonCredentials;

@Component
public class DataInit implements ApplicationRunner {
    private PartyDAO partyDAO;
    private PersonCredentialsDAO personCredentialsDAO;
    private PersonDAO personDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInit(PartyDAO partyDAO, PersonCredentialsDAO personCredentialsDAO, PersonDAO personDAO, PasswordEncoder passwordEncoder) {
        this.partyDAO = partyDAO;
        this.personCredentialsDAO = personCredentialsDAO;
        this.personDAO = personDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        partyDAO.save(new Party("Практическое занятие группы ИКБО-06-19"));
        partyDAO.save(new Party("Практическое занятие группы ИКБО-07-19"));
        partyDAO.save(new Party("Практическое занятие группы ИКБО-11-19"));
        partyDAO.save(new Party("Практическое занятие группы ИКБО-12-19"));

        personCredentialsDAO.save(
                new PersonCredentials(
                        "admin",
                        passwordEncoder.encode("admin"),
                        PersonCredentials.Role.ADMIN,
                        personDAO.save(new Person("admin", "admin"))
                )
        );
        personCredentialsDAO.save(
                new PersonCredentials(
                        "moder",
                        passwordEncoder.encode("moder"),
                        PersonCredentials.Role.MODER,
                        personDAO.save(new Person("moder", "moder"))
                )
        );
    }
}
