package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.Ruolo;
import it.unicam.cs.ids.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByName(RoleName roleName);
}
