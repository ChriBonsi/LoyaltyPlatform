package it.unicam.cs.ids.models;

import javax.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Analista extends UtenteGenerico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_an")
    @SequenceGenerator(name = "seq_an", allocationSize = 1)
    private Integer id;

    public Analista() {
        super();
    }

    @Override
    public String toString() {
        return "Analista{" + "id=" + id + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Analista analista = (Analista) o;
        return Objects.equals(id, analista.id);
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
