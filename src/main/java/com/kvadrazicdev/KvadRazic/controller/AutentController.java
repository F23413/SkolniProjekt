package com.kvadrazicdev.KvadRazic.controller;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.dto.ZadostPrihlaseni;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;
import com.kvadrazicdev.KvadRazic.service.interfate.IUzivateleSluzba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autent")
public class AutentController {
    @Autowired
    private IUzivateleSluzba uzivateleSluzba;
    @PostMapping("/registrace")
    public ResponseEntity<Odpoved> registrace(@RequestBody Uzivatel uzivatel){
        Odpoved odpoved = uzivateleSluzba.rejstrik(uzivatel);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }

    @PostMapping("/login")
    public ResponseEntity<Odpoved> login(@RequestBody ZadostPrihlaseni zadostPrihlaseni){
        Odpoved odpoved = uzivateleSluzba.prihlaseni(zadostPrihlaseni);
        return ResponseEntity.status(odpoved.getKodStavu()).body(odpoved);
    }
}
