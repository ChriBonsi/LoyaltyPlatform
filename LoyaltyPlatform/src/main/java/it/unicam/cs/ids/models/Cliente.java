package it.unicam.cs.ids.models;

import javax.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Cliente extends UtenteGenerico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cli")
    @SequenceGenerator(name = "seq_cli", allocationSize = 1)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    private Tessera tessera;

    public Cliente() {
        super();
        tessera = new Tessera();
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", tessera=" + tessera + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(tessera, cliente.tessera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, tessera);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }
}
