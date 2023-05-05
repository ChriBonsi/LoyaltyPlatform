package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Commerciante;
import it.unicam.cs.ids.models.UtenteGenerico;
import it.unicam.cs.ids.repositories.CommercianteRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

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

    //TODO DA POSTARE LE TRANSAZIONI + METTERE IL METODO ANCHE DENTRO COMMERCIANTE IMPORTANDOLO DA TRANSAZIONE
    @PostMapping
    public void addCommerciante(@RequestBody TemplateCommerciante request) {
        Commerciante commerciante = new Commerciante();
        this.setAllFields(commerciante, request);
        commercianteRepository.save(commerciante);
    }

    @PutMapping("{commerciante_id}")
    public void updateCommerciante(@PathVariable("commerciante_id") Integer id, @RequestBody TemplateCommerciante update) {
        if (commercianteRepository.findById(id).isPresent()) {
            Commerciante commerciante = commercianteRepository.getReferenceById(id);
            this.setAllFields(commerciante, update);
            commercianteRepository.save(commerciante);
        }
    }

    public void setAllFields(Commerciante toUpdate, TemplateCommerciante toSet){
        toUpdate.setNome(toSet.nome());
        toUpdate.setCognome(toSet.cognome());
        toUpdate.setEmail(toSet.email());
        toUpdate.setDataNascita(toSet.dataNascita());
        toUpdate.setNumeroTelefono(toSet.numeroTelefono());
        toUpdate.setRagioneSociale(toSet.ragioneSociale());
        toUpdate.setPartitaIVA(toSet.partitaIVA());
        toUpdate.setIndirizzo(toSet.indirizzo());
    }
    
    private record TemplateCommerciante(String nome, String cognome, String email, Date dataNascita, String numeroTelefono, String ragioneSociale, String partitaIVA, String indirizzo) {
    }
}
