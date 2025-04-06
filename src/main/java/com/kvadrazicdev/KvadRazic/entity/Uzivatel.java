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
}
