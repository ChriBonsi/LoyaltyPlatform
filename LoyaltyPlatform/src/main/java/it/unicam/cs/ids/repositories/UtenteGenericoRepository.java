package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.UtenteGenerico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteGenericoRepository extends JpaRepository<UtenteGenerico, Integer> {
    UtenteGenerico findByEmail(String email);
}
