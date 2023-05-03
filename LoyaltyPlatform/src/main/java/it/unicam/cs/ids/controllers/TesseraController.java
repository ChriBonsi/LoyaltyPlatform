package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Cliente;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/tessere")
public class TesseraController {

    private final TesseraRepository tesseraRepo;

    public TesseraController(TesseraRepository tesseraRepo) {
        this.tesseraRepo = tesseraRepo;
    }

    @GetMapping
    public List<Tessera> getTessere() {
        return tesseraRepo.findAll();
    }

    @PostMapping
    public void addTessera(@RequestBody TemplateTessera request) {
        Tessera tessera = new Tessera();
        tessera.setPunteggio(request.punteggio());
        tessera.setLivello(request.livello());
        tessera.setDataCreazione(new Date(System.currentTimeMillis()));
        tesseraRepo.save(tessera);
    }

    //DA VEDERE
    @PutMapping("{tesseraID}")
    public void updateTessera(@PathVariable("tesseraID") Integer id, @RequestBody TesseraController.TemplateTessera updated) {
        if (tesseraRepo.findById(id).isPresent()) {
            Tessera tessera = tesseraRepo.findById(id).get();
            tessera.setLivello(updated.livello());
            tessera.setPunteggio(updated.punteggio());
            tesseraRepo.save(tessera);
        }
    }
    //DA VEDERE
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
