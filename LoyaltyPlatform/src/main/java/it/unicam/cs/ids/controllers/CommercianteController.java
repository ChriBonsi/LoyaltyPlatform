package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Commerciante;
import it.unicam.cs.ids.models.PianoVip;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.models.Transazione;
import it.unicam.cs.ids.repositories.CommercianteRepository;
import it.unicam.cs.ids.repositories.PianoVipRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static it.unicam.cs.ids.controllers.ClienteController.VipAttivo;
import static it.unicam.cs.ids.models.UtenteGenerico.setCampiNonNulli;
import static it.unicam.cs.ids.models.UtenteGenerico.setCampiUtente;

@RestController
@RequestMapping("/commercianti")
public class CommercianteController {

    @Autowired
    private CommercianteRepository commercianteRepository;

    @Autowired
    private TesseraRepository tesseraRepository;

    @Autowired
    private PianoVipRepository pianoVIPRepository;

    @GetMapping
    public List<Commerciante> getCommercianti() {
        return commercianteRepository.findAll();
    }

    @GetMapping("{idCommerciante}")
    public Commerciante getCommerciante(@PathVariable("idCommerciante") Integer id) {
        return commercianteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addCommerciante(@RequestBody Commerciante request) {
        Commerciante commerciante = new Commerciante();
        setAllCampi(commerciante, request);
        commercianteRepository.save(commerciante);
    }

    @PostMapping("{commerciante_id}/adesione_piano_vip/{tessera_id}")
    public void adesionePianoVIP(@PathVariable("tessera_id") Integer id, @PathVariable("commerciante_id") Integer idCommerciante) {
        Commerciante commerciante;
        if (commercianteRepository.findById(idCommerciante).isPresent()) {
            commerciante = commercianteRepository.getReferenceById(idCommerciante);
        } else {
            commerciante = null;
        }

        if (tesseraRepository.findById(id).isPresent()) {
            Tessera tessera = tesseraRepository.getReferenceById(id);

            if (!VipAttivo(tessera)) {
                System.out.println(!VipAttivo(tessera));
                PianoVip toAdd = new PianoVip(true);
                toAdd.setTessera(tessera);
                tessera.getListaPianiVip().add(toAdd);

                Transazione puntiVIP = new Transazione();
                puntiVIP.setImportoTransazione(0.0);
                puntiVIP.setDescrizioneTransazione("Aggiunta premio iscrizione al programma VIP");
                puntiVIP.setDataTransazione(new Date(System.currentTimeMillis()));
                puntiVIP.setTessera(tessera);
                puntiVIP.setCommerciante(commerciante);
                puntiVIP.setOffertaUsata(null);

                tessera.getCronologiaTransazioni().add(puntiVIP);

                tessera.aggiuntaPunti(200, 200);

                pianoVIPRepository.save(toAdd);
            } else {
                System.out.println("Il cliente ha già un piano VIP attivo");
            }
        }
    }

    @PutMapping("{commerciante_id}")
    public void updateCommerciante(@PathVariable("commerciante_id") Integer id, @RequestBody Commerciante update) {
        if (commercianteRepository.findById(id).isPresent()) {
            Commerciante commerciante = commercianteRepository.getReferenceById(id);
            setAllCampi(commerciante, update);
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
            setCampiNonNulli(commerciante, update);
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

    public static void setAllCampi(Commerciante toUpdate, Commerciante toSet) {
        setCampiUtente(toUpdate, toSet);
        toUpdate.setRagioneSociale(toSet.getRagioneSociale());
        toUpdate.setPartitaIVA(toSet.getPartitaIVA());
        toUpdate.setIndirizzo(toSet.getIndirizzo());
    }
}
