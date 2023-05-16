package it.unicam.cs.ids.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Transazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trans")
    @SequenceGenerator(name = "seq_trans", initialValue = 10001, allocationSize = 1)
    private Integer id;

    private final static double PUNTI_PER_EURO = 0.5;

    @Column(length = 7)
    private Double importoTransazione;
    private Date dataTransazione;
    private String descrizioneTransazione;

    @ManyToOne
    @JoinColumn(name = "commerciante_id")
    @JsonBackReference
    private Commerciante commerciante;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    @JsonBackReference
    private Tessera tessera;


    @OneToOne
    private Offerta offertaUsata;

    public Transazione(Integer id, Double importoTransazione, Date dataTransazione, String descrizioneTransazione, Offerta offertaUsata) {
        this.id = id;
        this.importoTransazione = importoTransazione;
        this.dataTransazione = dataTransazione;
        this.descrizioneTransazione = descrizioneTransazione;
        this.offertaUsata = offertaUsata;
    }

    public Transazione() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transazione that = (Transazione) o;
        return Objects.equals(id, that.id) && Objects.equals(importoTransazione, that.importoTransazione) && Objects.equals(dataTransazione, that.dataTransazione) && Objects.equals(descrizioneTransazione, that.descrizioneTransazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, importoTransazione, dataTransazione, descrizioneTransazione);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataTransazione() {
        return dataTransazione;
    }

    public void setDataTransazione(Date dataTransazione) {
        this.dataTransazione = dataTransazione;
    }

    public String getDescrizioneTransazione() {
        return descrizioneTransazione;
    }

    public void setDescrizioneTransazione(String descrizioneTransazione) {
        this.descrizioneTransazione = descrizioneTransazione;
    }

    public Commerciante getCommerciante() {
        return commerciante;
    }

    public void setCommerciante(Commerciante commerciante) {
        this.commerciante = commerciante;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public Offerta getOffertaUsata() {
        return offertaUsata;
    }

    public void setOffertaUsata(Offerta offertaUsata) {
        this.offertaUsata = offertaUsata;
    }

    public Double getImportoTransazione() {
        return importoTransazione;
    }

    public void setImportoTransazione(Double importoTransazione) {
        this.importoTransazione = importoTransazione;
    }

    public Integer convertiInPunti() {
        return (int) (importoTransazione * PUNTI_PER_EURO);
    }

    @SuppressWarnings({"ConstantConditions"})
    public Integer ricalcolaPunteggio(Offerta offerta) {
        if (offerta == null) {
            return nettoPositivo(offerta);
        } else {
            return nettoPositivo(offerta) - offerta.getPuntiNecessari();
        }
    }

    public Integer nettoPositivo(Offerta offerta) {
        if (offerta == null) {
            return this.convertiInPunti();
        } else {
            return offerta.getPuntiBonus() + (int) (this.convertiInPunti() * offerta.getMoltiplicatore());
        }
    }

    public Integer getIdCommerciante() {
        return commerciante.getId();
    }

    public Integer getIdTessera() {
        return tessera.getId();
    }

    public Integer getIdOffertaUsata() {
        if (offertaUsata == null) {
            return null;
        } else {
            return offertaUsata.getId();
        }
    }
}
