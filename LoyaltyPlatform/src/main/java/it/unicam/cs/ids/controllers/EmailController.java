package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.message.mail.EmailDetails;
import it.unicam.cs.ids.message.mail.EmailService;
import it.unicam.cs.ids.models.Cliente;
import it.unicam.cs.ids.models.Offerta;
import it.unicam.cs.ids.models.PianoVip;
import it.unicam.cs.ids.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/sendMail")
    public String inviaMail(@RequestBody EmailDetails dettagli) {
        String status = emailService.inviaMail(dettagli);

        return status;
    }

    @PostMapping("/sendMailWithAttachment")
    public String inviaMailConAllegato(@RequestBody EmailDetails dettagli) {
        String status = emailService.inviaMailConAllegato(dettagli);

        return status;
    }

    public PianoVip mailScadenze() {
        List<Cliente> tuttiClienti = clienteRepository.findAll();

        PianoVip toReturn = null;

        for (Cliente cliente : tuttiClienti) {

            List<Offerta> offerteCliente = cliente.getTessera().getListaCoupon();
            List<PianoVip> pianiVip = cliente.getTessera().getListaPianiVip();
            String indMail = cliente.getEmail();

            if (pianiVip != null) {
                for (PianoVip piano : pianiVip) {
                    if (piano.isAttivo()) {
                        long giorni = giorniAllaData(piano.getDataScadenza());
                        if (giorni == 0) {
                            piano.setAttivo(false);
                            toReturn = piano;
                        } else if (giorni >= 1 && giorni <= 7) {
                            emailService.inviaMail(new EmailDetails(indMail, "Salve, " + cliente.getNome() + " " + cliente.getCognome() + "!\n" + "Il suo abbonamento al Piano Fedeltà VIP scadrà tra " + giorni + " giorni.\n" + "Speriamo di riaverla con noi anche il prossimo anno,\n" + "Cordiali saluti.", "Scadenza offerta"));
                        } else if (giorni == 30) {
                            emailService.inviaMail(new EmailDetails(indMail, "Salve, " + cliente.getNome() + " " + cliente.getCognome() + "!\n" + "Il suo abbonamento al Piano Fedeltà VIP scadrà tra un mese.\n" + "Speriamo di riaverla con noi anche il prossimo anno,\n" + "Cordiali saluti.", "Scadenza offerta"));
                        }
                    }
                }
            }

            for (Offerta offerta : offerteCliente) {
                long giorni = giorniAllaData(offerta.getDataScadenza());
                if (giorni >= 1 && giorni <= 7) {
                    emailService.inviaMail(new EmailDetails(indMail, "Salve, " + cliente.getNome() + " " + cliente.getCognome() + "!\n" + "La sua offerta " + offerta.getNomeOfferta() + " scadrà tra " + giorni + " giorni.\n" + "Cordiali saluti.", "Scadenza offerta"));
                }

            }
        }
        return toReturn;
    }

    private static long giorniAllaData(Date scad) {
        long milliGiorno = 86400000;
        long scadenza = scad.getTime();
        Date temp = new Date(System.currentTimeMillis());
        long oggi = temp.getTime();
        System.out.println(oggi);
        System.out.println(scadenza);
        System.out.println(((scadenza - oggi) / milliGiorno));

        if (scad.toString().equals(temp.toString())) {
            return 0;
        } else {
            return ((scadenza - oggi) / milliGiorno) + 1;
        }
    }
}
