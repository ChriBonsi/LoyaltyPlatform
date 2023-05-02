package it.unicam.cs.ids.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Tessera {
    @Id
    @GeneratedValue
    private String idTessera;
    private int punteggio;
    private int livello;

    public Tessera(String idTessera, int punteggio, int livello) {
        this.idTessera = idTessera;
        this.punteggio = punteggio;
        this.livello = livello;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tessera tessera = (Tessera) o;
        return Objects.equals(idTessera, tessera.idTessera) && Objects.equals(livello, tessera.livello) && Objects.equals(punteggio, tessera.punteggio);
    }
    @Override
    public int hashCode(){return Objects.hash(idTessera, punteggio, livello); }

    public String toString() {
        return "Tessera{" +
                "idTessera=" + idTessera +
                ", punteggio='" + punteggio + '\'' +
                ", livello='" + livello + '\'' +
                '}';
    }

    public String getIdTessera() {
        return idTessera;
    }

    public void setIdTessera(String idTessera) {
        this.idTessera = idTessera;
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
}
