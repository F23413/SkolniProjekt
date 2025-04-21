package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kvadrazicdev.KvadRazic.entity.Film;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PujckaDTO {
    private Long id;
    private LocalDate datumPujceni;
    private LocalDate datumVraceni;
    private int pocetMomentalnePujcenychFilmu;
    private String kodPotvrzeniZapujceni;
    private UzivatelDTO uzivatelPujcuje;
    private FilmDTO filmPujceny;

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

    public UzivatelDTO getUzivatelPujcuje() {
        return uzivatelPujcuje;
    }

    public void setUzivatelPujcuje(UzivatelDTO uzivatelPujcuje) {
        this.uzivatelPujcuje = uzivatelPujcuje;
    }

    public FilmDTO getFilmPujceny() {
        return filmPujceny;
    }

    public void setFilmPujceny(FilmDTO filmPujceny) {
        this.filmPujceny = filmPujceny;
    }
}
