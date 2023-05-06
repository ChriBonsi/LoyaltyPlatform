package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.models.Transazione;
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
    private CommercianteRepository commercianteRepository;

    public TransazioneController(TransazioneRepository transazioneRepository, TesseraRepository tesseraRepository, CommercianteRepository commercianteRepository) {
        this.transazioneRepository = transazioneRepository;
        this.tesseraRepository = tesseraRepository;
        this.commercianteRepository = commercianteRepository;
    }

    @GetMapping("{idTransazione}")
    public Transazione getTransazioni(@PathVariable("idTransazione") Integer id) {
        return transazioneRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Transazione> getTransazioni() {
        return transazioneRepository.findAll();
    }

    @PostMapping
    public void addTransazione(@RequestBody TemplateTransazione request) {
        Transazione transazione = new Transazione();
        transazione.setQuantitaPunti(request.quantitaPunti()); //TODO CONVERTIRE SOLDI SPESI IN PUNTI DA FAR AGGIUNGERE PER QUANTITAPUNTI
        transazione.setDataTransazione(new Date(System.currentTimeMillis()));
        transazione.setDescrizioneTransazione(request.descrizioneTransazione());
        transazione.setCommerciante(commercianteRepository.getReferenceById(request.idCommerciante()));
        Tessera nuova = tesseraRepository.getReferenceById(request.idTessera());
        transazione.setTessera(nuova);

        //TODO aggiunta a lista transazioni
        //TODO aggiunta punti

        transazioneRepository.save(transazione);
    }

    @PutMapping("{transazione_id}")
    public void updateTransazione(@PathVariable("transazione_id") Integer id, @RequestBody TemplateTransazione update, @PathVariable String transazione_id) {
        if (transazioneRepository.findById(id).isPresent()) {
            Transazione nuova = transazioneRepository.getReferenceById(id);
            nuova.setDataTransazione(update.dataTransazione());
            nuova.setDescrizioneTransazione(update.descrizioneTransazione());
            transazioneRepository.save(nuova);
        }
    }

    @DeleteMapping("{idTransazione}")
    public void deleteTransazione(@PathVariable("idTransazione") Integer id) {
        if (transazioneRepository.findById(id).isPresent()) {
            Transazione transazione = transazioneRepository.getReferenceById(id);

            transazione.getTessera().removeTransazione(transazione);

            transazioneRepository.deleteById(id);
        }
    }

    @DeleteMapping
    public void deleteAllTransazioni() {
        transazioneRepository.deleteAll();
    }
    


    record TemplateTransazione(Integer quantitaPunti, Date dataTransazione, String descrizioneTransazione, Integer idTessera, Integer idCommerciante, Integer idOfferta) {
    }
}
