package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Odpoved {
    public int getKodStavu() {
        return kodStavu;
    }

    public void setKodStavu(int kodStavu) {
        this.kodStavu = kodStavu;
    }

    public String getZprava() {
        return zprava;
    }

    public void setZprava(String zprava) {
        this.zprava = zprava;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCasExpirace() {
        return casExpirace;
    }

    public void setCasExpirace(String casExpirace) {
        this.casExpirace = casExpirace;
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

    public PujckaDTO getoPujcce() {
        return oPujcce;
    }

    public void setOPujcce(PujckaDTO oPujcce) {
        this.oPujcce = oPujcce;
    }

    public List<UzivatelDTO> getSeznamUzivatelu() {
        return seznamUzivatelu;
    }

    public void setSeznamUzivatelu(List<UzivatelDTO> seznamUzivatelu) {
        this.seznamUzivatelu = seznamUzivatelu;
    }

    public List<PujckaDTO> getSeznamPujcek() {
        return seznamPujcek;
    }

    public void setSeznamPujcek(List<PujckaDTO> seznamPujcek) {
        this.seznamPujcek = seznamPujcek;
    }

    public List<FilmDTO> getSeznamFilmu() {
        return seznamFilmu;
    }

    public void setSeznamFilmu(List<FilmDTO> seznamFilmu) {
        this.seznamFilmu = seznamFilmu;
    }

    private int kodStavu;
    private String zprava;

    private String token;
    private String role;
    private String casExpirace;
    private String kodPotvrzeniZapujceni;

    private UzivatelDTO uzivatelPujcuje;
    private FilmDTO filmPujceny;
    private PujckaDTO oPujcce;
    private List<UzivatelDTO> seznamUzivatelu;
    private List<PujckaDTO> seznamPujcek;
    private List<FilmDTO> seznamFilmu;
}
