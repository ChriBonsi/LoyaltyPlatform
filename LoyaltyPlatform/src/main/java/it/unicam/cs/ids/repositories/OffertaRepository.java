package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffertaRepository extends JpaRepository<Offerta, Integer> {
}
