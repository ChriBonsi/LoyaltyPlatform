package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Tessera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TesseraRepository extends JpaRepository<Tessera, Integer> {
}
