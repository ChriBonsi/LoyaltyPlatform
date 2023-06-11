package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Analista;
import it.unicam.cs.ids.repositories.AnalistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.unicam.cs.ids.models.UtenteGenerico.setCampiNonNulli;
import static it.unicam.cs.ids.models.UtenteGenerico.setCampiUtente;

@RestController
@RequestMapping("/analisti")
public class AnalistaController {

    @Autowired
    public AnalistaRepository analistaRepository;

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
    public void deleteAllAnalisti() {
        analistaRepository.deleteAll();
    }

    @PutMapping("{idAnalista}")
    public void updateAnalista(@PathVariable("idAnalista") Integer id, @RequestBody Analista update) {
        if (analistaRepository.findById(id).isPresent()) {
            Analista analista = analistaRepository.getReferenceById(id);
            setCampiUtente(analista, update);
        }
    }

    @PatchMapping("{idAnalista}")
    public void patchAnalista(@PathVariable("idAnalista") Integer id, @RequestBody Analista update) {
        if (analistaRepository.findById(id).isPresent()) {
            Analista analista = analistaRepository.getReferenceById(id);
            setCampiNonNulli(analista, update);
        }
    }
}
