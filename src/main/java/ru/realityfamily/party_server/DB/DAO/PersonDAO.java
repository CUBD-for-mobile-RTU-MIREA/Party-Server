package ru.realityfamily.party_server.DB.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.realityfamily.party_server.Models.Person;

import java.util.UUID;

public interface PersonDAO extends JpaRepository<Person, UUID> {
    public Person findByIdLike(UUID id);
}
