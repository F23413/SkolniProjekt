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
    private Long Id;

    @NotNull(message = "Datum půjčení je nezbytné")
    private LocalDate DatumPujceni;
    @Future(message = "Datum vrácení musí být v budoucnu")
    private LocalDate DatumVraceni;

    @Min(value = 0, message = "Počet zapůjčených filmů nesmí být menší než 0")
    private int PocetMomentalnePujcenychFilmu;
    private String KodPotvrzeniZapujceni;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_uzivatele")
    private Uzivatel UzivatelPujcuje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filmu")
    private Film FilmPujceny;

    @Override
    public String toString() {
        return "Pujcka{" +
                "Id=" + Id +
                ", DatumPujceni=" + DatumPujceni +
                ", DatumVraceni=" + DatumVraceni +
                ", PocetMomentalnePujcenychFilmu=" + PocetMomentalnePujcenychFilmu +
                ", KodPotvrzeniZapujceni='" + KodPotvrzeniZapujceni + '\'' +
                '}';
    }
}
