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
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/auth")
public class AuthRESTController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

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
    public <T extends UtenteGenerico> ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest, T templateUtente) {

        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already taken."), HttpStatus.BAD_REQUEST);
        }

        // Crea account
        Account account = new Account(signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));

        String ruolo = signUpRequest.getRuolo();
        Set<Ruolo> ruoli = new HashSet<>();

        //TODO finire la creazione dei profili
        switch (ruolo) {
            case "cliente":
                // crea cliente
                Cliente cliente = new Cliente();
                clienteRepository.save(cliente);
                account.setUniqueRole_id(cliente.getId());
                aggiungiConControllo(RoleName.CLIENTE, ruoli);
                break;

            case "commerciante":
                // create commerciante
                Commerciante commerciante = new Commerciante();
                commercianteRepository.save(commerciante);
                account.setUniqueRole_id(commerciante.getId());
                aggiungiConControllo(RoleName.COMMERCIANTE, ruoli);
                break;

            case "analista":
                Analista analista = new Analista();
                analistaRepository.save(analista);
                account.setUniqueRole_id(analista.getId());
                aggiungiConControllo(RoleName.ANALISTA, ruoli);
                break;

            case "admin":
                Amministratore amministratore = new Amministratore();
                amministratoreRepository.save(amministratore);
                account.setUniqueRole_id(amministratore.getId());
                aggiungiConControllo(RoleName.ADMIN, ruoli);

                break;

            default:
                Ruolo ruoloAccount = roleRepository.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Fail -> Cause: User Role not found."));
                ruoli.add(ruoloAccount);
        }

        account.setRoles(ruoli);
        System.out.println(account.getUniqueRole_id());
        accountRepository.save(account);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully."), HttpStatus.OK);
    }

    private void aggiungiConControllo(RoleName nome, Set<Ruolo> set) {
        nome.trasforma().forEach(ruolo -> {
            Ruolo daAggiungere = roleRepository.findByName(ruolo).orElseThrow(() -> new RuntimeException("Fail -> Cause: " + ruolo + " Role not found."));
            set.add(daAggiungere);
        });
    }
}
