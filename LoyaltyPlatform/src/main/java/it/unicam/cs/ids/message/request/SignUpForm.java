package it.unicam.cs.ids.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String email;

    private String ruolo;
    private Long unique_role_id;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUnique_role_id() {
        return unique_role_id;
    }

    public void setUnique_role_id(Long unique_role_id) {
        this.unique_role_id = unique_role_id;
    }
}
