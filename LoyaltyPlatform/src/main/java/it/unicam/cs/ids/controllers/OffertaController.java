package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Offerta;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offerte")
public class OffertaController {

    private final OffertaRepository offertaRepository;
    private final TesseraRepository tesseraRepository;

    public OffertaController(OffertaRepository offertaRepository, TesseraRepository tesseraRepository) {
        this.offertaRepository = offertaRepository;
        this.tesseraRepository = tesseraRepository;
    }

    @GetMapping
    public List<Offerta> getOfferte() {
        return offertaRepository.findAll();
    }

    @GetMapping("{idOfferta}")
    public Offerta getOfferta(@PathVariable("idOfferta") Integer id) {
        return offertaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addOfferta(@RequestBody Offerta request) {
        Offerta offerta = new Offerta();
        this.setAllFields(offerta, request);

        List<Tessera> toCheck = tesseraRepository.findAll();
        for (Tessera tessera : toCheck) {
            if (tessera.getLivello() >= offerta.getLivello()) {
                tessera.addCoupon(offerta);
            }
        }
        offertaRepository.save(offerta);
    }

    @PutMapping("{offertaID}")
    public void updateOfferta(@PathVariable("offertaID") Integer id, @RequestBody Offerta updated) {
        if (offertaRepository.findById(id).isPresent()) {
            Offerta offerta = offertaRepository.getReferenceById(id);
            this.setAllFields(offerta, updated);
            offertaRepository.save(offerta);
        }
    }

    @DeleteMapping("{idOfferta}")
    public void deleteOfferta(@PathVariable("idOfferta") Integer id) {
        if (offertaRepository.findById(id).isPresent()) {

            Offerta offerta = offertaRepository.getReferenceById(id);
            for (Tessera tessera : tesseraRepository.findAll()) {
                if (tessera.getListaCoupon().contains(offerta)) {
                    tessera.removeCoupon(offerta);
                }
            }
            offertaRepository.deleteById(id);
        }
    }

    @PatchMapping("{idOfferta}")
    public void patchOfferta(@PathVariable("idOfferta") Integer id, @RequestBody Offerta update) {
        if (offertaRepository.findById(id).isPresent()) {
            Offerta offerta = offertaRepository.getReferenceById(id);
            if (update.getLivello() != null) {
                offerta.setLivello(update.getLivello());
            }
            if (update.getDataInizio() != null) {
                offerta.setDataInizio(update.getDataInizio());
            }
            if (update.getDataScadenza() != null) {
                offerta.setDataScadenza(update.getDataScadenza());
            }
            if (update.getNomeOfferta() != null) {
                offerta.setNomeOfferta(update.getNomeOfferta());
            }
            if (update.getDescrizioneOfferta() != null) {
                offerta.setDescrizioneOfferta(update.getDescrizioneOfferta());
            }
            if (update.getPuntiNecessari() != null) {
                offerta.setPuntiNecessari(update.getPuntiNecessari());
            }
            if (update.getPuntiBonus() != null) {
                offerta.setPuntiBonus(update.getPuntiBonus());
            }
            if (update.getMoltiplicatore() != null) {
                offerta.setMoltiplicatore(update.getMoltiplicatore());
            }
        }
    }

    @DeleteMapping
    public void deleteAllOfferte() {
        offertaRepository.deleteAll();
    }

    private void setAllFields(Offerta offerta, Offerta updated) {
        offerta.setLivello(updated.getLivello());
        offerta.setDataInizio(updated.getDataInizio());
        offerta.setDataScadenza(updated.getDataScadenza());
        offerta.setDescrizioneOfferta(updated.getDescrizioneOfferta());
        offerta.setNomeOfferta(updated.getNomeOfferta());
        offerta.setPuntiNecessari(updated.getPuntiNecessari());
        offerta.setPuntiBonus(updated.getPuntiBonus());
        offerta.setMoltiplicatore(updated.getMoltiplicatore());
        offerta.setConsumabile(updated.consumabile());
    }
}
