package ru.realityfamily.party_server.DB.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.realityfamily.party_server.Models.Party;

import java.util.List;
import java.util.UUID;

@Repository
public interface PartyDAO extends JpaRepository<Party, UUID> {

    public Party findByIdLike(UUID id);
}
