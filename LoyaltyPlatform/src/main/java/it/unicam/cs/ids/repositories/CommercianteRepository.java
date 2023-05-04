package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Commerciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercianteRepository extends JpaRepository<Commerciante, Integer> {
}
