package it.unicam.cs.ids.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Commerciante extends UtenteGenerico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //(strategy = GenerationType.SEQUENCE, generator = "seq_trans")
    //@SequenceGenerator(name = "seq_trans", initialValue = 50000, allocationSize = 1)
    private Integer id;

    private String ragioneSociale;

    private String partitaIVA;

    private String indirizzo;

    public Commerciante(String nome, String cognome, String email, String numeroTelefono, Date dataNascita, Integer id, String ragioneSociale, String partitaIVA, String indirizzo) {
        super(nome, cognome, email, numeroTelefono, dataNascita);
        this.id = id;
        this.ragioneSociale = ragioneSociale;
        this.partitaIVA = partitaIVA;
        this.indirizzo = indirizzo;
    }

    public Commerciante() {
        super();
    }

    @Override
    public String toString() {
        return "Commerciante{" + "id=" + id + ", ragioneSociale='" + ragioneSociale + '\'' + ", partitaIVA='" + partitaIVA + '\'' + ", indirizzo='" + indirizzo + '\'' + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Commerciante that = (Commerciante) o;
        return Objects.equals(id, that.id) && Objects.equals(ragioneSociale, that.ragioneSociale) && Objects.equals(partitaIVA, that.partitaIVA) && Objects.equals(indirizzo, that.indirizzo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, ragioneSociale, partitaIVA, indirizzo);
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }

    public void setPartitaIVA(String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
