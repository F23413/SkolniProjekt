package com.kvadrazicdev.KvadRazic.controller;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.service.interfate.IUzivateleSluzba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/uzivatele")
public class UzivatelController {
    @Autowired
    private IUzivateleSluzba uzivateleSluzba;

    @GetMapping("/vse")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Odpoved> getVsechnyUzivatele(){
        Odpoved odpoved = uzivateleSluzba.getVsechnyUzivatele();
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/get-by-id/{uzivatelId}")
    public ResponseEntity<Odpoved> getUzivateleDleId(@PathVariable("uzivatelId") String uzivatelId){
        Odpoved odpoved = uzivateleSluzba.getUzivateleById(uzivatelId);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @DeleteMapping("/delete/{uzivatelId}")
    public ResponseEntity<Odpoved> odstranUzivatele(@PathVariable("uzivatelId") String uzivatelId){
        Odpoved odpoved = uzivateleSluzba.odstranUzivatele(uzivatelId);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/get-profile-info")
    public ResponseEntity<Odpoved> getProfilZalogovanehoUzivatele(){
        Authentication autent = SecurityContextHolder.getContext().getAuthentication();
        String email = autent.getName();
        Odpoved odpoved = uzivateleSluzba.getMojeInfo(email);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @GetMapping("/get-uzivatel-pujcky/{uzivatelId}")
    public ResponseEntity<Odpoved> getHistoriePujcovaniUzivatele(@PathVariable("uzivatelId") String uzivatelId){
        Odpoved odpoved = uzivateleSluzba.getHistoriePujcovaniUzivatele(uzivatelId);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }
}
