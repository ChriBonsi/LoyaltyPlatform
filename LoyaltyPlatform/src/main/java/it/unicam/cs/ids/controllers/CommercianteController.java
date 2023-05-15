package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Commerciante;
import it.unicam.cs.ids.repositories.CommercianteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.unicam.cs.ids.models.UtenteGenerico.setNonNullFields;
import static it.unicam.cs.ids.models.UtenteGenerico.setUtenteFields;

@RestController
@RequestMapping("/commercianti")
public class CommercianteController {

    private final CommercianteRepository commercianteRepository;

    public CommercianteController(CommercianteRepository commercianteRepository) {
        this.commercianteRepository = commercianteRepository;
    }

    @GetMapping
    public List<Commerciante> getCommerciante() {
        return commercianteRepository.findAll();
    }

    @GetMapping("{idCommerciante}")
    public Commerciante getCommerciante(@PathVariable("idCommerciante") Integer id) {
        return commercianteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addCommerciante(@RequestBody Commerciante request) {
        Commerciante commerciante = new Commerciante();
        this.setAllFields(commerciante, request);
        commercianteRepository.save(commerciante);
    }

    @PutMapping("{commerciante_id}")
    public void updateCommerciante(@PathVariable("commerciante_id") Integer id, @RequestBody Commerciante update) {
        if (commercianteRepository.findById(id).isPresent()) {
            Commerciante commerciante = commercianteRepository.getReferenceById(id);
            this.setAllFields(commerciante, update);
            commercianteRepository.save(commerciante);
        }
    }

    @DeleteMapping("{idCommerciante}")
    public void deleteCommerciante(@PathVariable("idCommerciante") Integer id) {
        commercianteRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllCommercianti() {
        commercianteRepository.deleteAll();
    }

    @PatchMapping("{idCommerciante}")
    public void patchCommerciante(@PathVariable("idCommerciante") Integer id, @RequestBody Commerciante update) {
        if (commercianteRepository.findById(id).isPresent()) {
            Commerciante commerciante = commercianteRepository.getReferenceById(id);
            setNonNullFields(commerciante, update);
            if (update.getRagioneSociale() != null) {
                commerciante.setRagioneSociale(update.getRagioneSociale());
            }
            if (update.getPartitaIVA() != null) {
                commerciante.setPartitaIVA(update.getPartitaIVA());
            }
            if (update.getIndirizzo() != null) {
                commerciante.setIndirizzo(update.getIndirizzo());
            }
        }
    }

    public void setAllFields(Commerciante toUpdate, Commerciante toSet) {
        setUtenteFields(toUpdate, toSet);
        toUpdate.setRagioneSociale(toSet.getRagioneSociale());
        toUpdate.setPartitaIVA(toSet.getPartitaIVA());
        toUpdate.setIndirizzo(toSet.getIndirizzo());
    }
}
