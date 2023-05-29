package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.models.Transazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Integer> {
    List<Transazione> findAllByTessera(Tessera referenceById);
}
