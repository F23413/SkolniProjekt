package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Odpoved {
    private int KodStavu;
    private String Zprava;

    private String Token;
    private String Role;
    private String CasExpirace;
    private String KodPotvrzeniZapujceni;

    private UzivatelDTO UzivatelPujcuje;
    private FilmDTO FilmPujceny;
    private PujckaDTO OPujcce;
    private List<UzivatelDTO> SeznamUzivatelu;
    private List<PujckaDTO> SeznamPujcek;
    private List<FilmDTO> SeznamFilmu;
}
