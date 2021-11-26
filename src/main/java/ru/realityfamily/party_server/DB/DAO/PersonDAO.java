package ru.realityfamily.party_server.DB.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.realityfamily.party_server.Models.Person;

import java.util.UUID;

public interface PersonDAO extends JpaRepository<Person, UUID> {
    Person findByIdLike(UUID id);

    @Query("select p from Person p where p.credentials.id = (select c.id from PersonCredentials c where c.login = :userName)")
    Person findByUserName(@Param("userName") String userName);

}
