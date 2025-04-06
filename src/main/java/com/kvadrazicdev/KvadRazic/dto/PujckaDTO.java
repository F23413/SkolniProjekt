package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kvadrazicdev.KvadRazic.entity.Film;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PujckaDTO {
    private Long Id;
    private LocalDate DatumPujceni;
    private LocalDate DatumVraceni;
    private int PocetMomentalnePujcenychFilmu;
    private String KodPotvrzeniZapujceni;
    private UzivatelDTO UzivatelPujcuje;
    private FilmDTO FilmPujceny;
}
