package it.unicam.cs.ids.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.Objects;

@Entity
public class Offerta {
    @Id
    @GeneratedValue
    private int id;
    private int livello;

    //yyyy-[m]m-[d]d
    private Date dataInizio;
    private Date dataScadenza;

    public Offerta(int id, int livello, Date dataInizio, Date dataScadenza) {
        this.id = id;
        this.livello = livello;
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offerta offerta = (Offerta) o;
        return Objects.equals(id, offerta.id) && Objects.equals(livello, offerta.livello) && Objects.equals(dataInizio, offerta.dataInizio) && Objects.equals(dataScadenza, offerta.dataScadenza);
    }
    @Override
    public int hashCode(){return Objects.hash(id, livello, dataInizio, dataScadenza); }

    public String toString() {
        return "Offerta{" +
                "id=" + id +
                ", livello='" + livello + '\'' +
                ", dataInizio='" + dataInizio + '\'' +
                ", dataScadenza='" + dataScadenza + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
}
