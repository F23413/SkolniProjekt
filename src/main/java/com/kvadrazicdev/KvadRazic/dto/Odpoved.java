package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Odpoved {
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
