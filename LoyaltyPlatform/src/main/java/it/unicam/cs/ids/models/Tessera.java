package it.unicam.cs.ids.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Tessera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tess")
    @SequenceGenerator(name = "seq_tess", initialValue = 501, allocationSize = 1)
    private Integer id;

    @Column(length = 5)
    private Integer punteggioDisponibile;

    @Column(length = 5)
    private Integer punteggioTotale;

    @Column(length = 3)
    private Integer livello;
    private Date dataCreazione;

    @OneToOne(mappedBy = "tessera")
    private Cliente cliente;


    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Offerta> listaCoupon;
    //TODO lista cronologia delle offerte

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "tessera")
    private List<Transazione> cronologiaTransazioni = new ArrayList<>();

    public Tessera(Integer id, Integer punteggioDisponibile, Integer punteggioTotale, Integer livello, Date dataCreazione, Cliente cliente) {
        this.id = id;
        this.punteggioDisponibile = punteggioDisponibile;
        this.punteggioTotale = punteggioTotale;
        this.livello = livello;
        this.dataCreazione = dataCreazione;
        this.cliente = cliente;
        this.listaCoupon = new ArrayList<>();
    }

    public Tessera() {
    }

    public void addCoupon(Offerta offerta) {
        this.listaCoupon.add(offerta);
    }

    public void removeCoupon(Offerta offerta) {
        this.listaCoupon.remove(offerta);
    }

    public void removeAllCoupon() {
        this.listaCoupon.clear();
    }

    public void addTransazione(Transazione transazione) {
        this.cronologiaTransazioni.add(transazione);
    }

    public void removeTransazione(Transazione transazione) {
        this.cronologiaTransazioni.remove(transazione);
    }

    public void removeAllTransazioni() {
        this.cronologiaTransazioni.clear();
    }

    public String stampaCoupon() {
        StringBuilder s = new StringBuilder();
        for (Offerta offerta : listaCoupon) {
            s.append(offerta.getNomeOfferta()).append("\n");
        }
        return s.toString();
    }

    public static Tessera inizializzaNuovaTessera() {
        Tessera tessera = new Tessera();
        tessera.setPunteggioDisponibile(0);
        tessera.setPunteggioTotale(0);
        tessera.setLivello(0);
        tessera.setDataCreazione(new Date(System.currentTimeMillis()));
        return tessera;
    }

    public void aggiuntaPunti(Integer offsetPunteggio, Integer offsetPositivo) {
        this.punteggioDisponibile += offsetPunteggio;
        this.punteggioTotale += offsetPositivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tessera tessera = (Tessera) o;
        return Objects.equals(id, tessera.id) && Objects.equals(livello, tessera.livello) && Objects.equals(punteggioDisponibile, tessera.punteggioDisponibile) && Objects.equals(dataCreazione, tessera.dataCreazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, punteggioDisponibile, livello, dataCreazione);
    }

    public String toString() {
        return "Tessera{" + "idTessera=" + id + ", punteggio='" + punteggioDisponibile + '\'' + ", livello='" + livello + '\'' + ", dataCreazione='" + dataCreazione + '\'' + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idTessera) {
        this.id = idTessera;
    }

    public Integer getPunteggioDisponibile() {
        return punteggioDisponibile;
    }

    public Integer getLivello() {
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

    public List<Offerta> getListaCoupon() {
        return listaCoupon;
    }

    public void setListaCoupon(List<Offerta> listaCoupon) {
        this.listaCoupon = listaCoupon;
    }

    public List<Transazione> getCronologiaTransazioni() {
        return cronologiaTransazioni;
    }

    public void setCronologiaTransazioni(List<Transazione> cronologiaTransazioni) {
        this.cronologiaTransazioni = cronologiaTransazioni;
    }

    public void setPunteggioDisponibile(Integer punteggioDisponibile) {
        this.punteggioDisponibile = punteggioDisponibile;
    }

    public Integer getPunteggioTotale() {
        return punteggioTotale;
    }

    public void setPunteggioTotale(Integer punteggioTotale) {
        this.punteggioTotale = punteggioTotale;
    }
}
