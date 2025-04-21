package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UzivatelDTO {
    private Long id;
    private String jmeno;
    private String email;
    private String telCislo;
    private String role;
    private List<PujckaDTO> pujcky = new ArrayList<>();

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PujckaDTO> getPujcky() {
        return pujcky;
    }

    public void setPujcky(List<PujckaDTO> pujcky) {
        this.pujcky = pujcky;
    }
}
