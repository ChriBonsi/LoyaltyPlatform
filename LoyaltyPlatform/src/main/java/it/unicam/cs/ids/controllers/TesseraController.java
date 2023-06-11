package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static it.unicam.cs.ids.controllers.ClienteController.checkOfferte;
import static it.unicam.cs.ids.controllers.ClienteController.VipAttivo;

@RestController
@RequestMapping("/tessere")
public class TesseraController {

    @Autowired
    private TesseraRepository tesseraRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    @GetMapping
    public List<Tessera> getTessere() {
        return tesseraRepository.findAll();
    }

    @GetMapping("{idTessera}")
    public Tessera getTessera(@PathVariable("idTessera") Integer id) {
        return tesseraRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addTessera(@RequestBody Tessera request) {
        Tessera tessera = new Tessera();
        this.setAllCampi(tessera, request);
        tessera.setDataCreazione(new Date(System.currentTimeMillis()));

        tessera.setListaCoupon(checkOfferte(tessera.getLivello(), offertaRepository, VipAttivo(tessera)));

        tesseraRepository.save(tessera);
    }

    @PutMapping("{tesseraID}")
    public void updateTessera(@PathVariable("tesseraID") Integer id, @RequestBody Tessera request) {
        if (tesseraRepository.findById(id).isPresent()) {
            Tessera tessera = tesseraRepository.findById(id).get();
            this.setAllCampi(tessera, request);
            tessera.setDataCreazione(request.getDataCreazione());

            tesseraRepository.save(tessera);
        }
    }

    @DeleteMapping("{idTessera}")
    public void deleteTessera(@PathVariable("idTessera") Integer id) {
        tesseraRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllTessere() {
        tesseraRepository.deleteAll();
    }

    @PatchMapping("{idTessera}")
    public void patchTessera(@PathVariable("idTessera") Integer id, @RequestBody Tessera update) {
        if (tesseraRepository.findById(id).isPresent()) {
            Tessera tessera = tesseraRepository.getReferenceById(id);

            if (update.getPunteggioDisponibile() != null) {
                tessera.setPunteggioDisponibile(update.getPunteggioDisponibile());
            }
            if (update.getPunteggioTotale() != null) {
                tessera.setPunteggioTotale(update.getPunteggioTotale());
            }
            if (update.getLivello() != null) {
                tessera.setLivello(update.getLivello());
            }
            if (update.getDataCreazione() != null) {
                tessera.setDataCreazione(update.getDataCreazione());
            }
        }
    }

    public void setAllCampi(Tessera tessera, Tessera request) {
        tessera.setPunteggioDisponibile(request.getPunteggioDisponibile());
        tessera.setPunteggioTotale(request.getPunteggioTotale());
        tessera.setLivello(request.getLivello());
    }
}
