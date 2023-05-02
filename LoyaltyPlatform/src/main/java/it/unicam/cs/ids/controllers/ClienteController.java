package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Cliente;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.ClienteRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/clienti")
public class ClienteController {
    private final ClienteRepository repo;
    private final TesseraRepository tRepo;

    public ClienteController(ClienteRepository repo, TesseraRepository tRepo) {
        this.repo = repo;
        this.tRepo = tRepo;
    }

    @GetMapping
    public List<Cliente> getCustomers() {
        return repo.findAll();
    }

    @PostMapping
    public void addCustomer(@RequestBody TemplateCliente request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setCognome(request.cognome());
        cliente.setEmail(request.email());
        cliente.setDataNascita(request.dataNascita());
        cliente.setNumeroTelefono(request.numeroTelefono());

        cliente.setTessera(Tessera.inizializzaNuovaTessera());
        tRepo.save(cliente.getTessera());

        repo.save(cliente);
    }

    @DeleteMapping("{idCliente}")
    public void deleteCustomer(@PathVariable("idCliente") Integer id) {
        repo.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllCustomers() {
        repo.deleteAll();
    }

    @PutMapping("{customerID}")
    public void updateCustomerData(@PathVariable("customerID") Integer id, @RequestBody TemplateCliente updated) {
        if (repo.findById(id).isPresent()) {
            Cliente cliente = repo.findById(id).get();
            cliente.setDataNascita(updated.dataNascita());
            cliente.setEmail(updated.email());
            cliente.setNome(updated.nome());
            repo.save(cliente);
        }
    }

    private record TemplateCliente(String nome, String cognome, String email, Date dataNascita, String numeroTelefono) {
    }
}
