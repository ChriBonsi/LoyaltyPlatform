package it.unicam.cs.ids.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Tessera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tess")
    @SequenceGenerator(name = "seq_tess", initialValue = 100, allocationSize = 1)
    private Integer id;

    private Integer punteggio;
    private Integer livello;
    private Date dataCreazione;

    @OneToOne(mappedBy = "tessera")
    private Cliente cliente;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Offerta> listaCoupon;

    public Tessera(Integer id, Integer punteggio, Integer livello, Date dataCreazione) {
        this.id = id;
        this.punteggio = punteggio;
        this.livello = livello;
        this.dataCreazione = dataCreazione;
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

    public String stampaCoupon() {
        StringBuilder s = new StringBuilder();
        for (Offerta offerta : listaCoupon) {
            s.append(offerta.getNomeOfferta()).append("\n");
        }
        return s.toString();
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

    public List<Offerta> getListaCoupon() {
        return listaCoupon;
    }

    public void setListaCoupon(List<Offerta> listaCoupon) {
        this.listaCoupon = listaCoupon;
    }
}
