package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Amministratore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmministratoreRepository extends JpaRepository<Amministratore, Integer> {
}
