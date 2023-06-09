package it.unicam.cs.ids.models;

import javax.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Amministratore extends UtenteGenerico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_amm")
    @SequenceGenerator(name = "seq_amm", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Amministratore() {
    }

    @Override
    public String toString() {
        return "Amministratore{" + "id=" + id + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Amministratore that = (Amministratore) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
