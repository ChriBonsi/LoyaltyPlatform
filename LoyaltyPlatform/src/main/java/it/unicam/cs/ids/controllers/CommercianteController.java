package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Commerciante;
import it.unicam.cs.ids.models.Offerta;
import it.unicam.cs.ids.repositories.ClienteRepository;
import it.unicam.cs.ids.repositories.CommercianteRepository;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TransazioneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commercianti")
public class CommercianteController {
    private final CommercianteRepository commercianteRepository;
    private final OffertaRepository offertaRepository;
    private final TransazioneRepository transazioneRepository;
    private final ClienteRepository clienteRepository;

    public CommercianteController(CommercianteRepository commercianteRepository, OffertaRepository offertaRepository, TransazioneRepository transazioneRepository, ClienteRepository clienteRepository) {
        this.commercianteRepository = commercianteRepository;
        this.offertaRepository = offertaRepository;
        this.transazioneRepository = transazioneRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Commerciante> getCommerciante() {
        return commercianteRepository.findAll();
    }

    @GetMapping("{idCommerciante}")
    public Commerciante getCommerciante(@PathVariable("idCommerciante") Integer id) {
        return commercianteRepository.findById(id).orElse(null);
    }

    @PostMapping //DA POSTARE LE TRANSAZIONI + METTERE IL METODO ANCHE DENTRO COMMERCIANTE IMPORTANDOLO DA TRANSAZIONE



}
