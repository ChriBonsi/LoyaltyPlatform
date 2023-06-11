package it.unicam.cs.ids.message.mail;

public interface EmailService {

    String inviaMail(EmailDetails details);

    String inviaMailConAllegato(EmailDetails details);
}
