package com.kvadrazicdev.KvadRazic.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "filmy")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nazevFilmu;
    private String zanrFilmu;
    private BigDecimal cenaFilmu;
    private String obrazekFilmu;
    private String popisFilmu;
    @OneToMany(mappedBy = "filmPujceny", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pujcka> pujcky = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazevFilmu() {
        return nazevFilmu;
    }

    public void setNazevFilmu(String nazevFilmu) {
        this.nazevFilmu = nazevFilmu;
    }

    public String getZanrFilmu() {
        return zanrFilmu;
    }

    public void setZanrFilmu(String zanrFilmu) {
        this.zanrFilmu = zanrFilmu;
    }

    public BigDecimal getCenaFilmu() {
        return cenaFilmu;
    }

    public void setCenaFilmu(BigDecimal cenaFilmu) {
        this.cenaFilmu = cenaFilmu;
    }

    public String getObrazekFilmu() {
        return obrazekFilmu;
    }

    public void setObrazekFilmu(String obrazekFilmu) {
        this.obrazekFilmu = obrazekFilmu;
    }

    public String getPopisFilmu() {
        return popisFilmu;
    }

    public void setPopisFilmu(String popisFilmu) {
        this.popisFilmu = popisFilmu;
    }

    public List<Pujcka> getPujcky() {
        return pujcky;
    }

    public void setPujcky(List<Pujcka> pujcky) {
        this.pujcky = pujcky;
    }

    @Override
    public String toString() {
        return "Film{" +
                "Id=" + id +
                ", NazevFilmu='" + nazevFilmu + '\'' +
                ", ZanrFilmu='" + zanrFilmu + '\'' +
                ", CenaFilmu=" + cenaFilmu +
                ", ObrazekFilmu='" + obrazekFilmu + '\'' +
                ", PopisFilmu='" + popisFilmu + '\'' +
                '}';
    }
}
