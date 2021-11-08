package ru.realityfamily.party_server.DB.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.realityfamily.party_server.Models.PersonCredentials;

import java.util.UUID;

public interface PersonCredentialsDAO extends JpaRepository<PersonCredentials, UUID> {
    public PersonCredentials findByIdLike(UUID id);
    public PersonCredentials findByLoginLike(String login);

    public Boolean existsByLoginLike(String login);
}
