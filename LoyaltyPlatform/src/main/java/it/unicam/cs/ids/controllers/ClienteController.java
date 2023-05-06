package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Cliente;
import it.unicam.cs.ids.models.Offerta;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.ClienteRepository;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clienti")
public class ClienteController {
    private final ClienteRepository clienteRepository;
    private final TesseraRepository tesseraRepository;
    private final OffertaRepository offertaRepo;

    public ClienteController(ClienteRepository clienteRepository, TesseraRepository tesseraRepository, OffertaRepository offertaRepo) {
        this.clienteRepository = clienteRepository;
        this.tesseraRepository = tesseraRepository;
        this.offertaRepo = offertaRepo;
    }

    @GetMapping
    public List<Cliente> getCustomers() {
        return clienteRepository.findAll();
    }

    @GetMapping("{idCliente}")
    public Cliente getCustomer(@PathVariable("idCliente") Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addCustomer(@RequestBody TemplateCliente request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setCognome(request.cognome());
        cliente.setEmail(request.email());
        cliente.setDataNascita(request.dataNascita());
        cliente.setNumeroTelefono(request.numeroTelefono());

        Tessera tessera = Tessera.inizializzaNuovaTessera();
        tessera.setListaCoupon(checkOfferte(tessera.getLivello(), offertaRepo));
        cliente.setTessera(tessera);
        tesseraRepository.save(tessera);

        clienteRepository.save(cliente);
    }

    @DeleteMapping("{idCliente}")
    public void deleteCustomer(@PathVariable("idCliente") Integer id) {
        Tessera tc = clienteRepository.getReferenceById(id).getTessera();
        tc.removeAllCoupon();
        tc.removeAllTransazioni();
        clienteRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllCustomers() {
        clienteRepository.deleteAll();
    }

    @PutMapping("{customerID}")
    public void updateCustomerData(@RequestBody TemplateCliente updated, @PathVariable("customerID") Integer id) {
        if (clienteRepository.findById(id).isPresent()) {
            Cliente cliente = clienteRepository.getReferenceById(id);
            cliente.setDataNascita(updated.dataNascita());
            cliente.setEmail(updated.email());
            cliente.setNome(updated.nome());
            cliente.setCognome(updated.cognome());
            cliente.setNumeroTelefono(updated.numeroTelefono());
            clienteRepository.save(cliente);
        }
    }

    static List<Offerta> checkOfferte(Integer livello, OffertaRepository offertaRepo) {
        List<Offerta> listaCoupon = new ArrayList<>();
        for (Offerta o : offertaRepo.findAll()) {
            System.out.println("Offerta " + o.getId());
            if (o.getLivello() <= livello) {
                listaCoupon.add(o);
                System.out.print("da aggiungere");
            }
        }
        System.out.println(listaCoupon.stream().toString());
        return listaCoupon;
    }

    private record TemplateCliente(String nome, String cognome, String email, Date dataNascita, String numeroTelefono) {
    }
}
