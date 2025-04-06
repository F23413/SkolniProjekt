package com.kvadrazicdev.KvadRazic.repository;

import com.kvadrazicdev.KvadRazic.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("SELECT DISTINCT f.zanrFilmu FROM Film f")
    List<String> najdiDleZanru();

    @Query("SELECT f FROM Film f WHERE f.zanrFilmu LIKE %:zanrFilmu% AND f.id NOT IN (SELECT pj.filmPujceny.id FROM Pujcka pj WHERE "+
            "(pj.datumPujceni <= :datumVraceni) AND (pj.datumVraceni >= :datumPujceni))")
    List<Film> najdiDostupneFilmyDleDataAZanru(LocalDate datumPujceni, LocalDate datumVraceni, String zanrFilmu);

    @Query("SELECT f FROM Film f WHERE f.id NOT IN (SELECT p.filmPujceny.id FROM Pujcka p)")
    List<Film> vratDostupneFilmy();
}
