package com.kvadrazicdev.KvadRazic.repository;

import com.kvadrazicdev.KvadRazic.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("SELECT DISTINCT f.ZanrFilmu FROM Film f")
    List<String> najdiDleZanru();

    @Query("SELECT f FROM Film f WHERE f.ZanrFilmu LIKE %:ZanrFilmu% AND f.id NOT IN (SELECT pj.film.id FROM Pujcka pj WHERE "+
            "(pj.DatumPujceni <= :DatumVraceni) AND (pj.DatumVraceni >= :DatumPujceni))")
    List<Film> najdiDostupneFilmyDleDataAZanru(LocalDate DatumPujceni, LocalDate DatumVraceni, String ZanrFilmu);

    @Query("SELECT f FROM Film f WHERE f.id NOT IN (SELECT p.film.id FROM Pujcka p)")
    List<Film> vratDostupneFilmy();
}
