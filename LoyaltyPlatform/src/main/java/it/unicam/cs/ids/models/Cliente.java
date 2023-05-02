package it.unicam.cs.ids.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String numeroTelefono;

    //yyyy-[m]m-[d]d
    private Date dataNascita;

    @OneToOne(cascade = CascadeType.ALL)
    private Tessera tessera;

    public Cliente(Long id, String nome, String cognome, String email, String numeroTelefono, Date dataNascita) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.numeroTelefono = numeroTelefono;
        this.dataNascita = dataNascita;
    }

    public Cliente() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(cognome, cliente.cognome) && Objects.equals(email, cliente.email) && Objects.equals(numeroTelefono, cliente.numeroTelefono) && Objects.equals(dataNascita, cliente.dataNascita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, email, numeroTelefono, dataNascita);
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nome='" + nome + '\'' + ", cognome='" + cognome + '\'' + ", email='" + email + '\'' + ", numeroTelefono='" + numeroTelefono + '\'' + ", dataNascita=" + dataNascita + '\'' + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }
}
