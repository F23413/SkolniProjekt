package com.kvadrazicdev.KvadRazic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "uzivatele")
public class Uzivatel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vyplňte prosím pole Jméno")
    private String jmeno;
    @NotBlank(message = "Vyplňte prosím pole Email")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Vyplňte prosím pole Telefonní číslo")
    private String telCislo;
    @NotBlank(message = "Vyplňte prosím pole Heslo")
    private String heslo;
    private String role;
    @OneToMany(mappedBy = "uzivatelPujcuje", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pujcka> pujcky = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelCislo() {
        return telCislo;
    }

    public void setTelCislo(String telCislo) {
        this.telCislo = telCislo;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Pujcka> getPujcky() {
        return pujcky;
    }

    public void setPujcky(List<Pujcka> pujcky) {
        this.pujcky = pujcky;
    }
}
