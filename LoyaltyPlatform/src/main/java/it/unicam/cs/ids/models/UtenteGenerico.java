package it.unicam.cs.ids.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;

import java.sql.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class UtenteGenerico {

    @Column(length = 25)
    private String nome;

    @Column(length = 25)
    private String cognome;

    @Column(unique = true, length = 50)
    @Email
    private String email;

    @Column(length = 15)
    private String numeroTelefono;

    @Column
    private Date dataNascita;

    //Da qui sto aggiungendo roba del login

    //finisce la roba del login

    protected UtenteGenerico(String nome, String cognome, String email, String numeroTelefono, Date dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.numeroTelefono = numeroTelefono;
        this.dataNascita = dataNascita;
    }

    protected UtenteGenerico() {
    }

    public static <T extends UtenteGenerico> void setUtenteFields(T toUpdate, T sorgente) {
        toUpdate.setNome(sorgente.getNome());
        toUpdate.setCognome(sorgente.getCognome());
        toUpdate.setEmail(sorgente.getEmail());
        toUpdate.setNumeroTelefono(sorgente.getNumeroTelefono());
        toUpdate.setDataNascita(sorgente.getDataNascita());
    }

    public static <T extends UtenteGenerico> void setNonNullFields(T toUpdate, T sorgente) {
        if (toUpdate.getNome() != null) {
            sorgente.setNome(toUpdate.getNome());
        }
        if (toUpdate.getCognome() != null) {
            sorgente.setCognome(toUpdate.getCognome());
        }
        if (toUpdate.getDataNascita() != null) {
            sorgente.setDataNascita(toUpdate.getDataNascita());
        }
        if (toUpdate.getNumeroTelefono() != null) {
            sorgente.setNumeroTelefono(toUpdate.getNumeroTelefono());
        }
        if (toUpdate.getEmail() != null) {
            sorgente.setEmail(toUpdate.getEmail());
        }
    }
    
    @Override
    public String toString() {
        return "UtenteGenerico{" + "nome='" + nome + '\'' + ", cognome='" + cognome + '\'' + ", email='" + email + '\'' + ", numeroTelefono='" + numeroTelefono + '\'' + ", dataNascita=" + dataNascita + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtenteGenerico that = (UtenteGenerico) o;
        return Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(email, that.email) && Objects.equals(numeroTelefono, that.numeroTelefono) && Objects.equals(dataNascita, that.dataNascita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, email, numeroTelefono, dataNascita);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }
}
