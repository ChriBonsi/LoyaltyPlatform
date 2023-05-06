package it.unicam.cs.ids.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Offerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_off")
    @SequenceGenerator(name = "seq_off", initialValue = 1001, allocationSize = 1)
    private Integer id;

    @Column(length = 3)
    private Integer livello;

    //yyyy-[m]m-[d]d
    private Date dataInizio;
    private Date dataScadenza;

    private String nomeOfferta;

    private String descrizioneOfferta;
    private boolean consumabile;

    public Offerta(Integer livello, Date dataInizio, Date dataScadenza, String nomeOfferta, String descrizioneOfferta, boolean consumabile) {
        this.livello = livello;
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
        this.nomeOfferta = nomeOfferta;
        this.descrizioneOfferta = descrizioneOfferta;
        this.consumabile = consumabile;
    }

    public Offerta() {
    }

    @Override
    public String toString() {
        return "Offerta{" + "id=" + id + ", livello=" + livello + ", dataInizio=" + dataInizio + ", dataScadenza=" + dataScadenza + ", nomeOfferta='" + nomeOfferta + '\'' + ", descrizioneOfferta='" + descrizioneOfferta + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offerta offerta = (Offerta) o;
        return Objects.equals(id, offerta.id) && Objects.equals(livello, offerta.livello) && Objects.equals(dataInizio, offerta.dataInizio) && Objects.equals(dataScadenza, offerta.dataScadenza) && Objects.equals(nomeOfferta, offerta.nomeOfferta) && Objects.equals(descrizioneOfferta, offerta.descrizioneOfferta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, livello, dataInizio, dataScadenza, nomeOfferta, descrizioneOfferta);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLivello() {
        return livello;
    }

    public void setLivello(Integer livello) {
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

    public String getNomeOfferta() {
        return nomeOfferta;
    }

    public void setNomeOfferta(String nomeOfferta) {
        this.nomeOfferta = nomeOfferta;
    }

    public String getDescrizioneOfferta() {
        return descrizioneOfferta;
    }

    public void setDescrizioneOfferta(String descrizioneOfferta) {
        this.descrizioneOfferta = descrizioneOfferta;
    }

    public boolean consumabile() {
        return this.consumabile;
    }

    public void setConsumabile(boolean consumabile) {
        this.consumabile = consumabile;
    }
}
