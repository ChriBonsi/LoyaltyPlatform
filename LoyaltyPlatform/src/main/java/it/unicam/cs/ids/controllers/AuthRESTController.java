package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.message.request.LoginForm;
import it.unicam.cs.ids.message.request.SignUpForm;
import it.unicam.cs.ids.message.response.JwtResponse;
import it.unicam.cs.ids.message.response.ResponseMessage;
import it.unicam.cs.ids.models.*;
import it.unicam.cs.ids.repositories.*;
import it.unicam.cs.ids.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static it.unicam.cs.ids.controllers.ClienteController.checkOfferte;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/auth")
public class AuthRESTController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RuoloRepository ruoloRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CommercianteRepository commercianteRepository;

    @Autowired
    AmministratoreRepository amministratoreRepository;

    @Autowired
    AnalistaRepository analistaRepository;

    @Autowired
    TesseraRepository tesseraRepository;

    @Autowired
    OffertaRepository offertaRepository;

    @PostMapping("/test")
    public void test(@RequestBody Cliente cliente) {
        ClienteController a = new ClienteController(clienteRepository, tesseraRepository, offertaRepository);
        a.addCustomer(cliente);
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Account> account = accountRepository.findByEmail(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), account.get().getUniqueRole_id().toString()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already taken."), HttpStatus.BAD_REQUEST);
        } else {
            // Crea account
            Account account = new Account(signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));

            String ruolo = signUpRequest.getRuolo();
            Set<Ruolo> ruoli = new HashSet<>();

            //TODO finire la creazione dei profili
            switch (ruolo) {
                case "cliente":
                    // crea cliente
                    Cliente cliente = new Cliente();
                    setFields(cliente, signUpRequest);

                    Tessera tessera = Tessera.inizializzaNuovaTessera();
                    tessera.setListaCoupon(checkOfferte(tessera.getLivello(), offertaRepository));
                    cliente.setTessera(tessera);
                    tesseraRepository.save(tessera);

                    clienteRepository.save(cliente);

                    account.setUniqueRole_id(cliente.getId());
                    aggiungiConControllo(RoleName.CLIENTE, ruoli);
                    break;

                case "commerciante":
                    // create commerciante
                    Commerciante commerciante = new Commerciante();
                    setFields(commerciante, signUpRequest);
                    commerciante.setRagioneSociale(signUpRequest.getRagioneSociale());
                    commerciante.setPartitaIVA(signUpRequest.getPartitaIVA());
                    commerciante.setIndirizzo(signUpRequest.getIndirizzo());


                    commercianteRepository.save(commerciante);
                    account.setUniqueRole_id(commerciante.getId());
                    aggiungiConControllo(RoleName.COMMERCIANTE, ruoli);
                    break;

                case "analista":
                    Analista analista = new Analista();
                    setFields(analista, signUpRequest);

                    analistaRepository.save(analista);
                    account.setUniqueRole_id(analista.getId());
                    aggiungiConControllo(RoleName.ANALISTA, ruoli);
                    break;

                case "admin":
                    Amministratore amministratore = new Amministratore();
                    setFields(amministratore, signUpRequest);

                    amministratoreRepository.save(amministratore);
                    account.setUniqueRole_id(amministratore.getId());
                    aggiungiConControllo(RoleName.ADMIN, ruoli);
                    break;

                default:
            }

            account.setRoles(ruoli);
            System.out.println(account.getUniqueRole_id());
            accountRepository.save(account);

            return new ResponseEntity<>(new ResponseMessage("User registered successfully."), HttpStatus.OK);
        }
    }

    private static <T extends UtenteGenerico> void setFields(T utenteGenerico, SignUpForm signUpRequest) {
        utenteGenerico.setNome(signUpRequest.getNome());
        utenteGenerico.setCognome(signUpRequest.getCognome());
        utenteGenerico.setNumeroTelefono(signUpRequest.getNumeroTelefono());
        utenteGenerico.setEmail(signUpRequest.getEmail());
        utenteGenerico.setDataNascita(signUpRequest.getDataNascita());

        if (utenteGenerico.getClass() == Commerciante.class) {
            ((Commerciante) utenteGenerico).setRagioneSociale(signUpRequest.getRagioneSociale());
            ((Commerciante) utenteGenerico).setIndirizzo(signUpRequest.getIndirizzo());
            ((Commerciante) utenteGenerico).setPartitaIVA(signUpRequest.getPartitaIVA());
        }
    }

    private void aggiungiConControllo(RoleName nome, Set<Ruolo> set) {
        nome.trasforma().forEach(ruolo -> {
            Ruolo daAggiungere = ruoloRepository.findByName(ruolo).orElseThrow(() -> new RuntimeException("Fail -> Cause: " + ruolo + " Role not found."));
            set.add(daAggiungere);
        });
    }
}
