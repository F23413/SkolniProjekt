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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumPujceni() {
        return datumPujceni;
    }

    public void setDatumPujceni(LocalDate datumPujceni) {
        this.datumPujceni = datumPujceni;
    }

    public LocalDate getDatumVraceni() {
        return datumVraceni;
    }

    public void setDatumVraceni(LocalDate datumVraceni) {
        this.datumVraceni = datumVraceni;
    }

    public int getPocetMomentalnePujcenychFilmu() {
        return pocetMomentalnePujcenychFilmu;
    }

    public void setPocetMomentalnePujcenychFilmu(int pocetMomentalnePujcenychFilmu) {
        this.pocetMomentalnePujcenychFilmu = pocetMomentalnePujcenychFilmu;
    }

    public String getKodPotvrzeniZapujceni() {
        return kodPotvrzeniZapujceni;
    }

    public void setKodPotvrzeniZapujceni(String kodPotvrzeniZapujceni) {
        this.kodPotvrzeniZapujceni = kodPotvrzeniZapujceni;
    }

    public Uzivatel getUzivatelPujcuje() {
        return uzivatelPujcuje;
    }

    public void setUzivatelPujcuje(Uzivatel uzivatelPujcuje) {
        this.uzivatelPujcuje = uzivatelPujcuje;
    }

    public Film getFilmPujceny() {
        return filmPujceny;
    }

    public void setFilmPujceny(Film filmPujceny) {
        this.filmPujceny = filmPujceny;
    }

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
