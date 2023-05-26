package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Ruolo;
import it.unicam.cs.ids.repositories.RuoloRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ruoli")
public class RuoloController {

    public final RuoloRepository ruoloRepository;

    public RuoloController(RuoloRepository ruoloRepository) {
        this.ruoloRepository = ruoloRepository;
    }

    @GetMapping
    public List<Ruolo> getCustomers() {
        return ruoloRepository.findAll();
    }
}
