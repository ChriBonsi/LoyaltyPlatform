package it.unicam.cs.ids.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Amministratore extends UtenteGenerico{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Amministratore(String nome, String cognome, String email, String numeroTelefono, Date dataNascita, Integer id) {
        super(nome, cognome, email, numeroTelefono, dataNascita);
        this.id = id;
    }

    public Amministratore(Integer id) {
        this.id = id;
    }

    public Amministratore() {
    }

    @Override
    public String toString() {
        return "Amministratore{" +
                "id=" + id +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Amministratore that = (Amministratore) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    public String getCognome() {
        return super.getCognome();
    }

    @Override
    public void setCognome(String cognome) {
        super.setCognome(cognome);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getNumeroTelefono() {
        return super.getNumeroTelefono();
    }

    @Override
    public void setNumeroTelefono(String numeroTelefono) {
        super.setNumeroTelefono(numeroTelefono);
    }

    @Override
    public Date getDataNascita() {
        return super.getDataNascita();
    }

    @Override
    public void setDataNascita(Date dataNascita) {
        super.setDataNascita(dataNascita);
    }
}
