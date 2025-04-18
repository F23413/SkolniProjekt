package com.kvadrazicdev.KvadRazic.controller;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.service.interfate.IFilmuSluzba;
import com.kvadrazicdev.KvadRazic.service.interfate.IPujckySluzba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/filmy")
public class FilmController {
    @Autowired
    private IFilmuSluzba filmuSluzba;
    @Autowired
    private IPujckySluzba pujckySluzba;

    @PostMapping("/pridat")
    @PreAuthorize("hasAuthority('ADMIN')") // pouze ADMIN má přístup
    public ResponseEntity<Odpoved> pridejNovyFilm(
            @RequestParam(value = "foto", required = false)MultipartFile foto,
            @RequestParam(value = "cenaFilmu", required = false)BigDecimal cenaFilmu,
            @RequestParam(value = "nazevFilmu", required = false)String nazevFilmu,
            @RequestParam(value = "zanrFilmu", required = false)String zanrFilmu,
            @RequestParam(value = "popisFilmu", required = false)String popisFilmu
            ){
        if(foto == null || foto.isEmpty() || cenaFilmu == null || nazevFilmu.isBlank() ||  zanrFilmu == null || zanrFilmu.isBlank() || popisFilmu.isBlank()){
            Odpoved odpoved = new Odpoved();
            odpoved.setKodStavu(400);
            odpoved.setZprava("Ujistěte se, že jsou všechny pole vyplněny(foto, cenaFilmu, nazevFilmu, zanrFilmu, popisFilmu)");
            return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
        }
        Odpoved odpoved = filmuSluzba.pridejNovyFilm(foto,zanrFilmu, nazevFilmu, cenaFilmu, popisFilmu);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/vse")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Odpoved> getVsechnyFilmy(){
        Odpoved odpoved = filmuSluzba.getVsechnyFilmy();
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/zanry")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<String> getZanryFilmu(){
        return filmuSluzba.getVsechnyZanry();
    }

    @GetMapping("/film-by-id/{filmId}")
    public ResponseEntity<Odpoved> getFilmDleId(@PathVariable Long filmId){
        Odpoved odpoved = filmuSluzba.getFilmDleId(filmId);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/vsechny-mozne-filmy")
    public ResponseEntity<Odpoved> getMozneFilmy(){
        Odpoved odpoved = filmuSluzba.getVsechnyMozneFilmy();
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/mozne-filmy-dle-data-a-zanru")
    public ResponseEntity<Odpoved> mozneFilmyDleDataAZanru(
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumPujceni,
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumVraceni,
            @RequestParam(required = false)String zanrFilmu
    ){
        if(datumPujceni == null || datumVraceni == null || zanrFilmu == null || zanrFilmu.isBlank() ){
            Odpoved odpoved = new Odpoved();
            odpoved.setKodStavu(400);
            odpoved.setZprava("Ujistěte se, že jsou všechny pole vyplněny(datumPujceni, datumVraceni, zanrFilmu)");
            return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
        }
        Odpoved odpoved = filmuSluzba.getMozneFilmyDleDataAZanru(datumPujceni, datumVraceni, zanrFilmu);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/update/{filmId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Odpoved> updateFilm( @PathVariable Long filmId,
            @RequestParam(value = "foto", required = false)MultipartFile foto,
            @RequestParam(value = "cenaFilmu", required = false)BigDecimal cenaFilmu,
            @RequestParam(value = "nazevFilmu", required = false)String nazevFilmu,
            @RequestParam(value = "zanrFilmu", required = false)String zanrFilmu,
            @RequestParam(value = "popisFilmu", required = false)String popisFilmu
    ){
        Odpoved odpoved = filmuSluzba.updateFilm(filmId, nazevFilmu, zanrFilmu, cenaFilmu, foto, popisFilmu);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @DeleteMapping("/delete/{filmId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Odpoved> odstranFilm(@PathVariable("filmId") Long filmId){
        Odpoved odpoved = filmuSluzba.odstranFilm(filmId);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }
}
