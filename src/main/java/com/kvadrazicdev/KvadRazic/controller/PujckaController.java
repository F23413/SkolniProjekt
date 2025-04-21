package com.kvadrazicdev.KvadRazic.controller;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import com.kvadrazicdev.KvadRazic.service.interfate.IPujckySluzba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pujcky")
public class PujckaController {
    @Autowired
    private IPujckySluzba pujckySluzba;

    @PostMapping("pujcka-knihy/{filmId}/{uzivatelId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('UZIVATEL')")
    public ResponseEntity<Odpoved> ulozPujcky(@PathVariable Long filmId, @PathVariable Long uzivatelId, @RequestBody Pujcka pujckyZadost){
        Odpoved odpoved = pujckySluzba.ulozPujcku(filmId, uzivatelId, pujckyZadost);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/vsechny-pujcky")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Odpoved> getVsechnyPujcky(){
        Odpoved odpoved = pujckySluzba.getVsechnyPujcky();
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/get-by-kod-potvrzeni/{kodPotvrzeni}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Odpoved> getPujckuDleKodPotvrzeni(@PathVariable String kodPotvrzeni){
        Odpoved odpoved = pujckySluzba.findPujckuDleKodPotvrzeni(kodPotvrzeni);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @DeleteMapping("/zrusit/{pujckaId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('UZIVATEL')")
    public ResponseEntity<Odpoved> zrusPujcku(@PathVariable Long pujckaId){
        Odpoved odpoved = pujckySluzba.zrusPujcku(pujckaId);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }
}
