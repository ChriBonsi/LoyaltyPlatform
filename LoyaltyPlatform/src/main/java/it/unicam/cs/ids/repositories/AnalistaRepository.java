package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Analista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalistaRepository extends JpaRepository<Analista, Integer> {
}
