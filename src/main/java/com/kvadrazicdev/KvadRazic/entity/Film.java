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
