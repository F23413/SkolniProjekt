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
    private Long Id;

    private String NazevFilmu;
    private String ZanrFilmu;
    private BigDecimal CenaFilmu;
    private String ObrazekFilmu;
    private String PopisFilmu;
    private List<Pujcka> Pujcky = new ArrayList<>();

    @Override
    public String toString() {
        return "Film{" +
                "Id=" + Id +
                ", NazevFilmu='" + NazevFilmu + '\'' +
                ", ZanrFilmu='" + ZanrFilmu + '\'' +
                ", CenaFilmu=" + CenaFilmu +
                ", ObrazekFilmu='" + ObrazekFilmu + '\'' +
                ", PopisFilmu='" + PopisFilmu + '\'' +
                '}';
    }
}
