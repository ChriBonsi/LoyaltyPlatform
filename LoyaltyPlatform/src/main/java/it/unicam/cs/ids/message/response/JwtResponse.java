package it.unicam.cs.ids.message.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private String id;

    public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities, String id) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
        this.id = id;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}