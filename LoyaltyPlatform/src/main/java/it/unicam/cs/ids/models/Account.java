package it.unicam.cs.ids.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ruolo> ruolos = new HashSet<>();

    private Integer uniqueRole_id;

    public Account() {
    }

    public Account(@NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(min = 6, max = 100) String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Ruolo> getRoles() {
        return ruolos;
    }

    public void setRoles(Set<Ruolo> ruolos) {
        this.ruolos = ruolos;
    }


    public Integer getUniqueRole_id() {
        return uniqueRole_id;
    }

    public void setUniqueRole_id(Integer uniqueRole_id) {
        this.uniqueRole_id = uniqueRole_id;
    }
}
