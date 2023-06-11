package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Offerta;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.models.Transazione;
import it.unicam.cs.ids.repositories.CommercianteRepository;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import it.unicam.cs.ids.repositories.TransazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/transazioni")
public class TransazioneController {

    @Autowired
    private TransazioneRepository transazioneRepository;

    @Autowired
    private TesseraRepository tesseraRepository;

    @Autowired
    private CommercianteRepository commercianteRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    @GetMapping("{idTransazione}")
    public Transazione getTransazione(@PathVariable("idTransazione") Integer id) {
        return transazioneRepository.findById(id).orElse(null);
    }

    @GetMapping("/tessera/{idTessera}")
    public List<Transazione> getTransazioniByTessera(@PathVariable("idTessera") Integer id) {
        return transazioneRepository.findAllByTessera(tesseraRepository.getReferenceById(id));
    }

    @GetMapping
    public List<Transazione> getTransazioni() {
        return transazioneRepository.findAll();
    }

    @PostMapping
    public void addTransazione(@RequestBody TemplateTransazione request) {
        Transazione transazione = new Transazione();

        transazione.setImportoTransazione(request.importoTransazione());
        transazione.setDataTransazione(new Date(System.currentTimeMillis()));
        transazione.setDescrizioneTransazione(request.descrizioneTransazione());
        transazione.setCommerciante(commercianteRepository.getReferenceById(request.idCommerciante()));

        Tessera tessera = tesseraRepository.getReferenceById(request.idTessera());
        transazione.setTessera(tessera);

        Offerta offerta = null;

        if (offertaRepository.findById(request.idOfferta()).isPresent()) {
            offerta = offertaRepository.getReferenceById(request.idOfferta());

            if (tessera.getListaCoupon().contains(offerta)) {

                if (offerta.getPuntiNecessari() <= tessera.getPunteggioDisponibile()) {
                    transazione.setOffertaUsata(offertaRepository.getReferenceById(request.idOfferta()));
                    if (offerta.consumabile()) {
                        tessera.removeCoupon(offerta);
                    }
                } else {
                    offerta = null;
                    System.out.println("Punti insufficienti");
                }
            }
        }

        if (request.usaCashback()) {
            double diff = tessera.getCashbackDisponibile() - transazione.getImportoTransazione();

            if (diff <= 0) {
                tessera.setCashbackDisponibile(0);
            } else {
                tessera.setCashbackDisponibile(diff);
            }
        }

        tessera.addTransazione(transazione);
        tessera.aggiuntaPunti(transazione.ricalcolaPunteggio(offerta), transazione.nettoPositivo(offerta));
        tessera.aggiornaLivello();
        tessera.aggiuntaCashback(transazione.accumulaCashback());

        transazioneRepository.save(transazione);
    }

    @PutMapping("{transazione_id}")
    public void updateTransazione(@PathVariable("transazione_id") Integer id, @RequestBody Transazione update) {
        if (transazioneRepository.findById(id).isPresent()) {
            Transazione nuova = transazioneRepository.getReferenceById(id);
            nuova.setDataTransazione(update.getDataTransazione());
            nuova.setDescrizioneTransazione(update.getDescrizioneTransazione());
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

    @PatchMapping("{idTransazione}")
    public void patchTransazione(@PathVariable("idTransazione") Integer id, @RequestBody Transazione update) {
        if (transazioneRepository.findById(id).isPresent()) {
            Transazione transazione = transazioneRepository.getReferenceById(id);

            if (transazione.getDescrizioneTransazione() != null) {
                transazione.setDescrizioneTransazione(update.getDescrizioneTransazione());
            }
            if (transazione.getImportoTransazione() != null) {
                transazione.setImportoTransazione(update.getImportoTransazione());
            }
            if (transazione.getDataTransazione() != null) {
                transazione.setDataTransazione(update.getDataTransazione());
            }
            if (transazione.getCommerciante() != null) {
                transazione.setCommerciante(update.getCommerciante());
            }
        }
    }

    private record TemplateTransazione(Double importoTransazione, String descrizioneTransazione, Integer idTessera,
                                       Integer idCommerciante, @Nullable Integer idOfferta, boolean usaCashback) {
    }
}
