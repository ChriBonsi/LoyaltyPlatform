package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Amministratore;
import it.unicam.cs.ids.repositories.AmministratoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.unicam.cs.ids.models.UtenteGenerico.setCampiNonNulli;
import static it.unicam.cs.ids.models.UtenteGenerico.setCampiUtente;

@RestController
@RequestMapping("/amministratori")
public class AmministratoreController {

    @Autowired
    public AmministratoreRepository amministratoreRepository;

    @GetMapping("{idAmministratore}")
    public Amministratore getAmministratore(@PathVariable("idAmministratore") Integer id) {
        return amministratoreRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Amministratore> getAmministratori() {
        return amministratoreRepository.findAll();
    }

    @PostMapping
    public void addAmministratore(@RequestBody Amministratore request) {
        Amministratore amministratore = new Amministratore();
        setCampiUtente(amministratore, request);
        amministratoreRepository.save(amministratore);
    }

    @DeleteMapping("{idAmministratore}")
    public void deleteAmministratore(@PathVariable("idAmministratore") Integer id) {
        amministratoreRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllAmministratori() {
        amministratoreRepository.deleteAll();
    }

    @PutMapping("{idAmministratore}")
    public void updateAmministratore(@PathVariable("idAmministratore") Integer id, @RequestBody Amministratore request) {
        if (amministratoreRepository.findById(id).isPresent()) {
            Amministratore amministratore = amministratoreRepository.getReferenceById(id);
            setCampiUtente(amministratore, request);
            amministratoreRepository.save(amministratore);
        }
    }

    @PatchMapping("{idAmministratore}")
    public void patchAmministratore(@PathVariable("idAmministratore") Integer id, @RequestBody Amministratore update) {
        if (amministratoreRepository.findById(id).isPresent()) {
            Amministratore amministratore = amministratoreRepository.getReferenceById(id);
            setCampiNonNulli(amministratore, update);
        }
    }
}
