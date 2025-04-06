package com.kvadrazicdev.KvadRazic.repository;

import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PujckaRepository extends JpaRepository<Pujcka, Long> {
    List<Pujcka> najdiDleIdFilmu(Long IdPokoje);
    List<Pujcka> najdiDleKodPotvrzeniZapujceni(String KodPotvrzeni);
    List<Pujcka> najdiDleIdUzivatele(String IdUzivatel);
}
