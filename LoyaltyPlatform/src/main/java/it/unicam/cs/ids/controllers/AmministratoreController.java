package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Amministratore;
import it.unicam.cs.ids.repositories.AmministratoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.unicam.cs.ids.models.UtenteGenerico.setNonNullFields;
import static it.unicam.cs.ids.models.UtenteGenerico.setUtenteFields;

@RestController
@RequestMapping("/amministratori")
public class AmministratoreController {

    public AmministratoreRepository amministratoreRepository;

    public AmministratoreController(AmministratoreRepository amministratoreRepository) {
        this.amministratoreRepository = amministratoreRepository;
    }

    @GetMapping
    public List<Amministratore> getAmministratori() {
        return amministratoreRepository.findAll();
    }

    @GetMapping("{idAmministratore}")
    public Amministratore getAmministratore(@PathVariable("idAmministratore") Integer id) {
        return amministratoreRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addAmministratore(@RequestBody Amministratore temp) {
        Amministratore amministratore = new Amministratore();
        setUtenteFields(amministratore, temp);
        amministratoreRepository.save(amministratore);
    }

    @DeleteMapping("{idAmministratore}")
    public void deleteAdmin(@PathVariable("idAmministratore") Integer id) {
        amministratoreRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllAdmin() {
        amministratoreRepository.deleteAll();
    }

    @PutMapping("{idAmministratore}")
    public void updateAdmin(@PathVariable("idAmministratore") Integer id, @RequestBody Amministratore temp) {
        if (amministratoreRepository.findById(id).isPresent()) {
            Amministratore amministratore = amministratoreRepository.getReferenceById(id);
            setUtenteFields(amministratore, temp);
            amministratoreRepository.save(amministratore);
        }
    }

    @PatchMapping("{idAmministratore}")
    public void patchAmministratore(@PathVariable("idAmministratore") Integer id, @RequestBody Amministratore update) {
        if (amministratoreRepository.findById(id).isPresent()) {
            Amministratore amministratore = amministratoreRepository.getReferenceById(id);
            setNonNullFields(amministratore, update);
        }
    }
}
