package it.unicam.cs.ids.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
public class PianoVip {
    @Id
    @GeneratedValue
    private Integer id;
    boolean attivo;
    Date dataAdesione;
    Date dataScadenza;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    @JsonIgnore
    private Tessera tessera;

    public PianoVip(boolean attivo) {
        this.attivo = attivo;
        if (attivo) {
            this.dataAdesione = new Date(System.currentTimeMillis());

            LocalDate temp = LocalDate.now().plusYears(1);
            dataScadenza = Date.valueOf(temp);
        } else {
            this.dataAdesione = null;
            this.dataScadenza = null;
        }
    }

    public PianoVip() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public Date getDataAdesione() {
        return dataAdesione;
    }

    public void setDataAdesione(Date dataAdesione) {
        this.dataAdesione = dataAdesione;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public boolean isAttivo() {
        return attivo;
    }
}
