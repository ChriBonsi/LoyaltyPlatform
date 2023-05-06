package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static it.unicam.cs.ids.controllers.ClienteController.checkOfferte;

@RestController
@RequestMapping("/tessere")
public class TesseraController {

    private final TesseraRepository tesseraRepo;
    private final OffertaRepository offertaRepo;


    public TesseraController(TesseraRepository tesseraRepo, OffertaRepository offertaRepo) {
        this.tesseraRepo = tesseraRepo;
        this.offertaRepo = offertaRepo;
    }

    @GetMapping
    public List<Tessera> getTessere() {
        return tesseraRepo.findAll();
    }

    @GetMapping("{idTessera}")
    public Tessera getTessera(@PathVariable("idTessera") Integer id) {
        return tesseraRepo.findById(id).orElse(null);
    }

    @PostMapping
    public void addTessera(@RequestBody TemplateTessera request) {
        Tessera tessera = new Tessera();
        tessera.setPunteggio(request.punteggio());
        tessera.setLivello(request.livello());
        tessera.setDataCreazione(new Date(System.currentTimeMillis()));

        tessera.setListaCoupon(checkOfferte(tessera.getLivello(), offertaRepo));


        tesseraRepo.save(tessera);
    }

    @PutMapping("{tesseraID}")
    public void updateTessera(@PathVariable("tesseraID") Integer id, @RequestBody TesseraController.TemplateTessera updated) {
        if (tesseraRepo.findById(id).isPresent()) {
            Tessera tessera = tesseraRepo.findById(id).get();
            tessera.setLivello(updated.livello());
            tessera.setPunteggio(updated.punteggio());
            tesseraRepo.save(tessera);
        }
    }

    @DeleteMapping("{idTessera}")
    public void deleteTessera(@PathVariable("idTessera") Integer id) {
        tesseraRepo.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllTessere() {
        tesseraRepo.deleteAll();
    }

    private record TemplateTessera(Integer punteggio, Integer livello) {
    }
}
