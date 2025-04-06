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
}
