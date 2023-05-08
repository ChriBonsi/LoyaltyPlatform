package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Amministratore;
import it.unicam.cs.ids.repositories.AmministratoreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/amministratori")
public class AmministratoreController {

    public AmministratoreRepository amministratoreRepository;

    public AmministratoreController(AmministratoreRepository amministratoreRepository) {
        this.amministratoreRepository = amministratoreRepository;
    }

    @GetMapping
    public List<Amministratore> getAmministratore() {
        return amministratoreRepository.findAll();
    }

    @GetMapping("{idAmministratore}")
    public Amministratore getAmministratore(@PathVariable("idAmministratore") Integer id) {
        return amministratoreRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addAmministratore(@RequestBody TemplateAdmin temp) {
        Amministratore amministratore = new Amministratore();
        setFields(amministratore, temp);
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
    public void updateAdmin(@PathVariable("idAmministratore") Integer id, @RequestBody TemplateAdmin temp) {
        if (amministratoreRepository.findById(id).isPresent()) {
            Amministratore amministratore = amministratoreRepository.getReferenceById(id);
            setFields(amministratore, temp);
            amministratoreRepository.save(amministratore);
        }
    }

    private void setFields(Amministratore amministratore, TemplateAdmin temp) {
        amministratore.setNome(temp.nome());
        amministratore.setCognome(temp.cognome());
        amministratore.setEmail(temp.email());
        amministratore.setDataNascita(temp.dataNascita());
        amministratore.setNumeroTelefono(temp.numeroTelefono());
    }

    private record TemplateAdmin(String nome, String cognome, String email, Date dataNascita, String numeroTelefono) {
    }
}
