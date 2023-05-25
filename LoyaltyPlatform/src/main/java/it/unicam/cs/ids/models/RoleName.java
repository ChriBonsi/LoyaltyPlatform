package it.unicam.cs.ids.models;

import java.util.HashSet;
import java.util.Set;

public enum RoleName {
    CLIENTE, ADMIN, ANALISTA, COMMERCIANTE;

    public Set<RoleName> trasforma() {
        Set<RoleName> setRuoli = new HashSet<>();
        switch (this) {
            case ADMIN -> {
                setRuoli.add(RoleName.ADMIN);
                setRuoli.add(RoleName.ANALISTA);
                setRuoli.add(RoleName.COMMERCIANTE);
                setRuoli.add(RoleName.CLIENTE);
            }
            case ANALISTA -> {
                setRuoli.add(RoleName.ANALISTA);
                setRuoli.add(RoleName.COMMERCIANTE);
                setRuoli.add(RoleName.CLIENTE);
            }
            case COMMERCIANTE -> {
                setRuoli.add(RoleName.COMMERCIANTE);
                setRuoli.add(RoleName.CLIENTE);
            }
            case CLIENTE -> setRuoli.add(RoleName.CLIENTE);
            default -> {
            }
        }
        return setRuoli;
    }
}
