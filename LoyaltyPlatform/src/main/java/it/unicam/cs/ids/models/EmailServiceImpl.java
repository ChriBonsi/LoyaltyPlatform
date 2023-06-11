package it.unicam.cs.ids.models;

import it.unicam.cs.ids.message.mail.EmailDetails;
import it.unicam.cs.ids.message.mail.EmailService;

import java.io.File;
import java.util.Objects;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mittente;

    public String inviaMail(EmailDetails dettagli) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(mittente);
            mailMessage.setTo(dettagli.getDestinatario());
            mailMessage.setText(dettagli.getMsgBody());
            mailMessage.setSubject(dettagli.getOggetto());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String inviaMailConAllegato(EmailDetails dettagli) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mittente);
            mimeMessageHelper.setTo(dettagli.getDestinatario());
            mimeMessageHelper.setText(dettagli.getMsgBody());
            mimeMessageHelper.setSubject(dettagli.getOggetto());

            FileSystemResource file = new FileSystemResource(new File(dettagli.getAllegato()));

            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }
}
