package ru.realityfamily.party_server.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "person_credentials")
public class PersonCredentials {
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private Role role;

    @OneToOne
    @JoinColumn(name = "person_id")
    @JsonManagedReference
    private Person person;

    public PersonCredentials() {
    }

    public PersonCredentials(String login, String password, Role role, Person person) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.person = person;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        ADMIN,
        MODER,
        USER,
    }
}
