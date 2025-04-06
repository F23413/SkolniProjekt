package com.kvadrazicdev.KvadRazic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "pujcky")
public class Pujcka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Datum půjčení je nezbytné")
    private LocalDate datumPujceni;
    @Future(message = "Datum vrácení musí být v budoucnu")
    private LocalDate datumVraceni;

    @Min(value = 0, message = "Počet zapůjčených filmů nesmí být menší než 0")
    private int pocetMomentalnePujcenychFilmu;
    private String kodPotvrzeniZapujceni;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_uzivatele")
    private Uzivatel uzivatelPujcuje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filmu")
    private Film filmPujceny;

    @Override
    public String toString() {
        return "Pujcka{" +
                "Id=" + id +
                ", DatumPujceni=" + datumPujceni +
                ", DatumVraceni=" + datumVraceni +
                ", PocetMomentalnePujcenychFilmu=" + pocetMomentalnePujcenychFilmu +
                ", KodPotvrzeniZapujceni='" + kodPotvrzeniZapujceni + '\'' +
                '}';
    }
}
