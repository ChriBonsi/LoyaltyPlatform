package it.unicam.cs.ids.models;

import java.util.HashSet;
import java.util.Set;

public enum RoleName {
    CLIENTE, ADMIN, ANALISTA, USER, COMMERCIANTE;

    public Set<RoleName> trasforma() {
        Set<RoleName> setRuoli = new HashSet<>();
        switch (this) {
            case ADMIN:
                setRuoli.add(RoleName.ADMIN);
                setRuoli.add(RoleName.ANALISTA);
                setRuoli.add(RoleName.COMMERCIANTE);
                setRuoli.add(RoleName.CLIENTE);
                break;
            case ANALISTA:
                setRuoli.add(RoleName.ANALISTA);
                setRuoli.add(RoleName.COMMERCIANTE);
                setRuoli.add(RoleName.CLIENTE);
                break;
            case COMMERCIANTE:
                setRuoli.add(RoleName.COMMERCIANTE);
                setRuoli.add(RoleName.CLIENTE);
                break;
            case CLIENTE:
                setRuoli.add(RoleName.CLIENTE);
                break;
            default:
                break;
        }
        return setRuoli;
    }
}
