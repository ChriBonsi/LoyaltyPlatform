package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.models.Cliente;
import it.unicam.cs.ids.models.Offerta;
import it.unicam.cs.ids.models.PianoVip;
import it.unicam.cs.ids.models.Tessera;
import it.unicam.cs.ids.repositories.ClienteRepository;
import it.unicam.cs.ids.repositories.OffertaRepository;
import it.unicam.cs.ids.repositories.TesseraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.ids.models.UtenteGenerico.setCampiNonNulli;
import static it.unicam.cs.ids.models.UtenteGenerico.setCampiUtente;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TesseraRepository tesseraRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    @GetMapping
    public List<Cliente> getClienti() {
        return clienteRepository.findAll();
    }

    @GetMapping("{idCliente}")
    public Cliente getCliente(@PathVariable("idCliente") Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void addCliente(@RequestBody Cliente request) {
        Cliente cliente = new Cliente();
        setCampiUtente(cliente, request);

        Tessera tessera = Tessera.inizializzaNuovaTessera();
        tessera.setListaCoupon(checkOfferte(tessera.getLivello(), offertaRepository, VipAttivo(tessera)));
        cliente.setTessera(tessera);
        tesseraRepository.save(tessera);

        clienteRepository.save(cliente);
    }

    @DeleteMapping("{idCliente}")
    public void deleteCliente(@PathVariable("idCliente") Integer id) {
        Tessera tc = clienteRepository.getReferenceById(id).getTessera();
        tc.removeAllCoupon();
        tc.removeAllTransazioni();
        clienteRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllClienti() {
        clienteRepository.deleteAll();
    }

    @PutMapping("{customerID}")
    public void updateCliente(@RequestBody Cliente updated, @PathVariable("customerID") Integer id) {
        if (clienteRepository.findById(id).isPresent()) {
            Cliente cliente = clienteRepository.getReferenceById(id);
            setCampiUtente(cliente, updated);
            clienteRepository.save(cliente);
        }
    }

    @PatchMapping("{idCliente}")
    public void patchCliente(@PathVariable("idCliente") Integer id, @RequestBody Cliente update) {
        if (clienteRepository.findById(id).isPresent()) {
            Cliente cliente = clienteRepository.getReferenceById(id);
            setCampiNonNulli(cliente, update);
        }
    }

    static List<Offerta> checkOfferte(Integer livello, OffertaRepository offertaRepository, boolean vip) {
        List<Offerta> listaCoupon = new ArrayList<>();
        for (Offerta o : offertaRepository.findAll()) {
            if (o.getLivello() <= livello) {
                listaCoupon.add(o);
            } else if (o.getLivello() == 11 && vip) {
                listaCoupon.add(o);
            }
        }
        return listaCoupon;
    }

    static boolean VipAttivo(Tessera t) {
        List<PianoVip> listaPiani = t.getListaPianiVip();
        boolean flag = false;
        if (listaPiani != null) {

            for (PianoVip p : listaPiani) {
                if (p.isAttivo()) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
