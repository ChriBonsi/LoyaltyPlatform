package it.unicam.cs.ids.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Tessera {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer punteggio;
    private Integer livello;
    private Date dataCreazione;

    @OneToOne(mappedBy = "tessera")
    private Cliente cliente;

    public Tessera(Integer id, Integer punteggio, Integer livello, Date dataCreazione) {
        this.id = id;
        this.punteggio = punteggio;
        this.livello = livello;
        this.dataCreazione = dataCreazione;
    }

    public Tessera() {
    }

    public static Tessera inizializzaNuovaTessera() {
        Tessera tessera = new Tessera();
        tessera.setPunteggio(0);
        tessera.setLivello(0);
        tessera.setDataCreazione(new Date(System.currentTimeMillis()));
        return tessera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tessera tessera = (Tessera) o;
        return Objects.equals(id, tessera.id) && Objects.equals(livello, tessera.livello) && Objects.equals(punteggio, tessera.punteggio) && Objects.equals(dataCreazione, tessera.dataCreazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, punteggio, livello, dataCreazione);
    }

    public String toString() {
        return "Tessera{" + "idTessera=" + id + ", punteggio='" + punteggio + '\'' + ", livello='" + livello + '\'' + ", dataCreazione='" + dataCreazione + '\'' + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idTessera) {
        this.id = idTessera;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
}
