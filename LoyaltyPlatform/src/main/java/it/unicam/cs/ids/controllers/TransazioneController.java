package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.models.Transazione;
import it.unicam.cs.ids.repositories.ClienteRepository;
import it.unicam.cs.ids.repositories.CommercianteRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import it.unicam.cs.ids.repositories.TransazioneRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/transazioni")
public class TransazioneController {
    private final TransazioneRepository transazioneRepository;
    private final TesseraRepository tesseraRepository;

    public TransazioneController(TransazioneRepository transazioneRepository, TesseraRepository tesseraRepository) {
        this.transazioneRepository = transazioneRepository;
        this.tesseraRepository = tesseraRepository;
    }

    @GetMapping("{idTransazione}")
    public Transazione getTransazioni(@PathVariable("idTransazione") Integer id) {
        return transazioneRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addTransazione(@RequestBody TemplateTransazione request) {
        Transazione transazione = new Transazione();
        transazione.setQuantitaPunti(request.quantitaPunti());
        transazione.setDataTransazione(new Date(System.currentTimeMillis()));
        transazione.setDescrizioneTransazione(request.descrizioneTransazione());

        List<Tessera> toCheck = tesseraRepository.findAll(); //TODO DOVREBBE ESSERE UN METODO SIMILE, MA SONO TROPPO STANCO PER RAGIONARCI BENE
            /*for (Tessera tessera : toCheck) {
                for (tessera.getPunteggio() + transazione.getQuantitaPunti()){
                    tessera.addTransazione(transazione);
                }
            }*/
        transazioneRepository.save(transazione);
    }

    //TODO
    // BISOGNA METTERE UN METODO PER FAR AGGIUNGERE I PUNTI DELLA TRANSAZIONE NELLA TESSERA
    // FARE IL TEMPLATE PER TRANSAZIONE


    record TemplateTransazione(Integer quantitaPunti, Date dataTransazione, String descrizioneTransazione) {
    }
}
