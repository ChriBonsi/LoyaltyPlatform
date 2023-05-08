package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Transazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Integer> {
}
