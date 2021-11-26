package ru.realityfamily.party_server.Controllers.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.realityfamily.party_server.Models.Person;
import ru.realityfamily.party_server.Models.PersonCredentials;

@JsonIgnoreProperties({ "credentials" })
public class PersonPOJO extends Person {
    private PersonCredentials.Role role;

    public PersonPOJO(Person person) {
        setId(person.getId());
        setFirst_name(person.getFirst_name());
        setLast_name(person.getLast_name());
        setEmail(person.getEmail());
        setPhone(person.getPhone());
        setConnections(person.getConnections());
        setCredentials(person.getCredentials());
        this.role = person.getCredentials().getRole();
    }

    public PersonCredentials.Role getRole() {
        role = getCredentials().getRole();
        return role;
    }

    public void setRole(PersonCredentials.Role role) {
        getCredentials().setRole(role);
        this.role = role;
    }
}
