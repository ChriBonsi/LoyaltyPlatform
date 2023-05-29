package it.unicam.cs.ids.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    @SequenceGenerator(name = "seq_account", allocationSize = 1)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    private String ruolo;

    private Integer uniqueRole_id;

    public Account() {
    }

    public Account(@NotBlank @Size(min = 3, max = 50) String email, @NotBlank @Size(min = 6, max = 100) String password) {
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Integer getUniqueRole_id() {
        return uniqueRole_id;
    }

    public void setUniqueRole_id(Integer uniqueRole_id) {
        this.uniqueRole_id = uniqueRole_id;
    }
}
