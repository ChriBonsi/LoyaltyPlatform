package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Analista;
import it.unicam.cs.ids.repositories.AnalistaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.unicam.cs.ids.models.UtenteGenerico.setNonNullFields;
import static it.unicam.cs.ids.models.UtenteGenerico.setUtenteFields;

@RestController
@RequestMapping("/analisti")
public class AnalistaController {

    public AnalistaRepository analistaRepository;

    public AnalistaController(AnalistaRepository analistaRepository) {
        this.analistaRepository = analistaRepository;
    }

    @GetMapping
    public List<Analista> getAnalisti() {
        return analistaRepository.findAll();
    }

    @GetMapping("{idAnalista}")
    public Analista getAnalista(@PathVariable("idAnalista") Integer id) {
        return analistaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addAnalista(@RequestBody Analista analista) {
        analistaRepository.save(analista);
    }

    @DeleteMapping("{idAnalista}")
    public void deleteAnalista(@PathVariable("idAnalista") Integer id) {
        analistaRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllAnalista() {
        analistaRepository.deleteAll();
    }

    @PutMapping("{idAnalista}")
    public void updateAnalista(@PathVariable("idAnalista") Integer id, @RequestBody Analista update) {
        if (analistaRepository.findById(id).isPresent()) {
            Analista analista = analistaRepository.getReferenceById(id);
            setUtenteFields(analista, update);
        }
    }

    @PatchMapping("{idAnalista}")
    public void patchAnalista(@PathVariable("idAnalista") Integer id, @RequestBody Analista update) {
        if (analistaRepository.findById(id).isPresent()) {
            Analista analista = analistaRepository.getReferenceById(id);
            setNonNullFields(analista, update);
        }
    }
}