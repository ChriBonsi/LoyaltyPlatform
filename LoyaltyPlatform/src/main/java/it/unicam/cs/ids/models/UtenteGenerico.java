package it.unicam.cs.ids.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.sql.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class UtenteGenerico {

    @Column
    private String nome;

    @Column
    private String cognome;

    @Column
    private String email;

    @Column
    private String numeroTelefono;

    @Column
    private Date dataNascita;

    protected UtenteGenerico(String nome, String cognome, String email, String numeroTelefono, Date dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.numeroTelefono = numeroTelefono;
        this.dataNascita = dataNascita;
    }

    protected UtenteGenerico() {
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
