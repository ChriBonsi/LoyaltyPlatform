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
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/accounts")
    public List<Account> test() {
        return accountRepository.findAll();
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

            switch (ruolo) {
                case "cliente" -> {
                    // crea cliente
                    Cliente cliente = new Cliente();
                    setFields(cliente, signUpRequest);
                    Tessera tessera = Tessera.inizializzaNuovaTessera();
                    tessera.setListaCoupon(checkOfferte(tessera.getLivello(), offertaRepository));
                    cliente.setTessera(tessera);
                    tesseraRepository.save(tessera);
                    clienteRepository.save(cliente);
                    account.setUniqueRole_id(cliente.getId());
                }
                case "commerciante" -> {
                    // create commerciante
                    Commerciante commerciante = new Commerciante();
                    setFields(commerciante, signUpRequest);
                    commerciante.setRagioneSociale(signUpRequest.getRagioneSociale());
                    commerciante.setPartitaIVA(signUpRequest.getPartitaIVA());
                    commerciante.setIndirizzo(signUpRequest.getIndirizzo());
                    commercianteRepository.save(commerciante);
                    account.setUniqueRole_id(commerciante.getId());
                }
                case "analista" -> {
                    Analista analista = new Analista();
                    setFields(analista, signUpRequest);
                    analistaRepository.save(analista);
                    account.setUniqueRole_id(analista.getId());
                }
                case "admin" -> {
                    Amministratore amministratore = new Amministratore();
                    setFields(amministratore, signUpRequest);
                    amministratoreRepository.save(amministratore);
                    account.setUniqueRole_id(amministratore.getId());
                }
                default -> System.out.println("Ruolo non valido");

            }

            account.setRuolo(ruolo);
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
}
