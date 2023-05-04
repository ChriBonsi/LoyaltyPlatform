package it.unicam.cs.ids.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Transazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trans")
    @SequenceGenerator(name = "seq_trans", initialValue = 50000, allocationSize = 1)
    private Integer id;
    private Integer quantitaPunti;
    private Date dataTransazione;
    private String descrizioneTransazione;

    public Transazione(Integer id, Integer quantitaPunti, Date dataTransazione, String descrizioneTransazione) {
        this.id = id;
        this.quantitaPunti = quantitaPunti;
        this.dataTransazione = dataTransazione;
        this.descrizioneTransazione = descrizioneTransazione;
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

    public Integer getquantitaPunti() {
        return quantitaPunti;
    }

    public void setquantitaPunti(Integer quantitaPunti) {
        this.quantitaPunti = quantitaPunti;
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
}
