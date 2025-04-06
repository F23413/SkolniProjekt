package com.kvadrazicdev.KvadRazic.repository;

import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PujckaRepository extends JpaRepository<Pujcka, Long> {
    List<Pujcka> findByFilmPujceny_Id(Long filmId);
    List<Pujcka> findByKodPotvrzeniZapujceni(String kodPotvrzeniZapujceni);
    List<Pujcka> findByUzivatelPujcuje_Id(Long uzivatelId);
}
