package com.kvadrazicdev.KvadRazic.service.interfate;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IFilmuSluzba {
    Odpoved pridejNovyFilm(MultipartFile obrazekFilmu, BigDecimal cenaFilmu, String zanrFilmu, String nazevFilmu,  String popisFilmu);
    Odpoved getVsechnyFilmy();
    List<String> getVsechnyZanry();
    Odpoved odstranFilm(Long filmId);
    Odpoved updateFilm(Long filmId, String nazevFilmu, String zanrFilmu, BigDecimal cenaFilmu, MultipartFile obrazekFilmu, String popisFilmu);
    Odpoved getFilmDleId(Long filmId);
    Odpoved getMozneFilmyDleDataAZanru(LocalDate datumPujceni, LocalDate datumVraceni, String zanrFilmu);
    Odpoved getVsechnyMozneFilmy();
}
