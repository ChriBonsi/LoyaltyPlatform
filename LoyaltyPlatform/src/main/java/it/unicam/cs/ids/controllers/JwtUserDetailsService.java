package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.UtenteGenerico;
import it.unicam.cs.ids.repositories.UtenteGenericoRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UtenteGenericoRepository utenteGenericoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UtenteGenerico utenteGenerico = utenteGenericoRepository.findByEmail(email);
        if (utenteGenerico == null) {
            throw new UsernameNotFoundException("Email non trovata: " + email);
        }
        return utenteGenerico;
    }
}
