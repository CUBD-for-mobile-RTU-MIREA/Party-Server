package ru.realityfamily.party_server.Models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table
public class Person {
    @NonNull
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @OneToOne(mappedBy = "person")
    private PersonCredentials credentials;

    @ElementCollection
    private Map<String, String> connections;

    public Person() {
        id = UUID.randomUUID();
        connections = new HashMap<>();
    }

    public Person(String first_name, String last_name) {
        this();
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PersonCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(PersonCredentials credentials) {
        this.credentials = credentials;
    }

    public Map<String, String> getConnections() {
        return connections;
    }

    public void setConnections(Map<String, String> connections) {
        this.connections = connections;
    }
}
