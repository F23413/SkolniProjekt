package com.kvadrazicdev.KvadRazic.service.interfate;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;

public interface IPujckySluzba {
    Odpoved ulozPujcku(Long filmId, Long uzivatelId, Pujcka zadostPujcky);
    Odpoved findPujckuDleKodPotvrzeni(String kodPotvrzeniZapujceni);
    Odpoved getVsechnyPujcky();
    Odpoved zrusPujcku(Long pujckaId);
}
