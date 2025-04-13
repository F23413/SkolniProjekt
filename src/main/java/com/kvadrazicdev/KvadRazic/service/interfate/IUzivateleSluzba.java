package com.kvadrazicdev.KvadRazic.service.interfate;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.dto.ZadostPrihlaseni;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;

public interface IUzivateleSluzba {
    Odpoved rejstrik(Uzivatel uzivatel);
    Odpoved prihlaseni(ZadostPrihlaseni zadostPrihlaseni);
    Odpoved getVsechnyUzivatele();
    Odpoved getHistoriePujcovaniUzivatele(String uzivatelId);
    Odpoved odstranUzivatele(String uzivatelId);
    Odpoved getUzivateleById(String uzivatelId);
    Odpoved getMojeInfo(String email);
}
