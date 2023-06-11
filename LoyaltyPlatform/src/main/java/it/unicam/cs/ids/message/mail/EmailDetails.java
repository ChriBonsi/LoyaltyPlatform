package it.unicam.cs.ids.message.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailDetails {

    private String destinatario;
    private String msgBody;
    private String oggetto;
    private String allegato;

    public EmailDetails(String destinatario, String msgBody, String oggetto) {
        this.destinatario = destinatario;
        this.msgBody = msgBody;
        this.oggetto = oggetto;
    }
}
