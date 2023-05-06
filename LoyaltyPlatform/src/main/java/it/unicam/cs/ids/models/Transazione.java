package it.unicam.cs.ids.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Transazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trans")
    @SequenceGenerator(name = "seq_trans", initialValue = 10001, allocationSize = 1)
    private Integer id;

    @Column(length = 5)
    private Integer quantitaPunti;
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

    @Nullable
    @OneToOne
    private Offerta offertaUsata;

    public Transazione(Integer id, Integer quantitaPunti, Date dataTransazione, String descrizioneTransazione, @Nullable Offerta offertaUsata) {
        this.id = id;
        this.quantitaPunti = quantitaPunti;
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
        return Objects.equals(id, that.id) && Objects.equals(quantitaPunti, that.quantitaPunti) && Objects.equals(dataTransazione, that.dataTransazione) && Objects.equals(descrizioneTransazione, that.descrizioneTransazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantitaPunti, dataTransazione, descrizioneTransazione);
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

    @Nullable
    public Offerta getOffertaUsata() {
        return offertaUsata;
    }

    public void setOffertaUsata(@Nullable Offerta offertaUsata) {
        this.offertaUsata = offertaUsata;
    }

    public Integer getQuantitaPunti() {
        return quantitaPunti;
    }

    public void setQuantitaPunti(Integer quantitaPunti) {
        this.quantitaPunti = quantitaPunti;
    }
}
