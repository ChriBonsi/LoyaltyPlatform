package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Offerta;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/offerte")
public class OffertaController {

    private final OffertaRepository offertaRepo;
    private final TesseraRepository tesseraRepo;

    public OffertaController(OffertaRepository offertaRepo, TesseraRepository tesseraRepo) {
        this.offertaRepo = offertaRepo;
        this.tesseraRepo = tesseraRepo;
    }

    @GetMapping
    public List<Offerta> getOfferte() {
        return offertaRepo.findAll();
    }

    @GetMapping("{idOfferta}")
    public Offerta getOfferta(@PathVariable("idOfferta") Integer id) {
        return offertaRepo.findById(id).orElse(null);
    }

    @PostMapping
    public void addOfferta(@RequestBody TemplateOfferta request) {
        Offerta offerta = new Offerta();
        offerta.setLivello(request.livello());
        offerta.setDataInizio(request.dataInizio());
        offerta.setDataScadenza(request.dataScadenza());
        offerta.setNomeOfferta(request.nomeOfferta());
        offerta.setDescrizioneOfferta(request.descrizioneOfferta());
        offerta.setConsumabile(request.consumabile());//TODO FARE CRONOLOGIA OFFERTE USATE

        List<Tessera> toCheck = tesseraRepo.findAll();
        for (Tessera tessera : toCheck) {
            if (tessera.getLivello() >= offerta.getLivello()) {
                tessera.addCoupon(offerta);
            }
        }
        offertaRepo.save(offerta);
    }

    @PutMapping("{offertaID}")
    public void updateOfferta(@PathVariable("offertaID") Integer id, @RequestBody TemplateOfferta updated) {
        if (offertaRepo.findById(id).isPresent()) {
            Offerta offerta = offertaRepo.getReferenceById(id);
            offerta.setLivello(updated.livello());
            offerta.setDataInizio(updated.dataInizio());
            offerta.setDataScadenza(updated.dataScadenza());
            offerta.setDescrizioneOfferta(updated.descrizioneOfferta());
            offertaRepo.save(offerta);
        }
    }

    @DeleteMapping("{idOfferta}")
    public void deleteOfferta(@PathVariable("idOfferta") Integer id) {
        if (offertaRepo.findById(id).isPresent()) {

            Offerta offerta = offertaRepo.getReferenceById(id);
            for (Tessera tessera : tesseraRepo.findAll()) {
                if (tessera.getListaCoupon().contains(offerta)) {
                    tessera.removeCoupon(offerta);
                }
            }
            offertaRepo.deleteById(id);
        }
    }

    @DeleteMapping
    public void deleteAllOfferte() {
        offertaRepo.deleteAll();
    }

    private record TemplateOfferta(Integer livello, Date dataInizio, Date dataScadenza, String nomeOfferta,
                                   String descrizioneOfferta, boolean consumabile) {
    }
}
