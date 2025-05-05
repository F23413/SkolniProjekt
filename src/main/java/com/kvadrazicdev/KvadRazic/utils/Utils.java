package com.kvadrazicdev.KvadRazic.utils;

import com.kvadrazicdev.KvadRazic.dto.FilmDTO;
import com.kvadrazicdev.KvadRazic.dto.PujckaDTO;
import com.kvadrazicdev.KvadRazic.dto.UzivatelDTO;
import com.kvadrazicdev.KvadRazic.entity.Film;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generujRandomAlfanumer(int length){ // generuje potvrzovaci kod
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++){
            int randIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randChar = ALPHANUMERIC_STRING.charAt(randIndex);
            stringBuilder.append(randChar);
        }
        return stringBuilder.toString();
    }

    public static UzivatelDTO mapUzivatelEntituNaUzivatelDTO(Uzivatel uzivatel){
        UzivatelDTO uzivatelDTO = new UzivatelDTO();

        uzivatelDTO.setId(uzivatel.getId());
        uzivatelDTO.setJmeno(uzivatel.getJmeno());
        uzivatelDTO.setEmail(uzivatel.getEmail());
        uzivatelDTO.setTelCislo(uzivatel.getTelCislo());
        uzivatelDTO.setRole(uzivatel.getRole());

        return uzivatelDTO;
    }

    public static FilmDTO mapFilmEntituNaFilmDTO(Film film){
        FilmDTO filmDTO = new FilmDTO();

        filmDTO.setId(film.getId());
        filmDTO.setNazevFilmu(film.getNazevFilmu());
        filmDTO.setCenaFilmu(film.getCenaFilmu());
        filmDTO.setZanrFilmu(film.getZanrFilmu());
        filmDTO.setPopisFilmu(film.getPopisFilmu());
        filmDTO.setObrazekFilmu(film.getObrazekFilmu());

        return filmDTO;
    }

    public static PujckaDTO mapPujckaEntituNaPujckaDTO(Pujcka pujcka){
        PujckaDTO pujckaDTO = new PujckaDTO();

        pujckaDTO.setId(pujcka.getId());
        pujckaDTO.setDatumPujceni(pujcka.getDatumPujceni());
        pujckaDTO.setDatumVraceni(pujcka.getDatumVraceni());
        pujckaDTO.setPocetMomentalnePujcenychFilmu(pujcka.getPocetMomentalnePujcenychFilmu());
        pujckaDTO.setKodPotvrzeniZapujceni(pujcka.getKodPotvrzeniZapujceni());
        return pujckaDTO;
    }

    public static FilmDTO mapFilmEntituNaFilmDTOPlusPujcka(Film film){
        FilmDTO filmDTO = new FilmDTO();

        filmDTO.setId(film.getId());
        filmDTO.setNazevFilmu(film.getNazevFilmu());
        filmDTO.setCenaFilmu(film.getCenaFilmu());
        filmDTO.setZanrFilmu(film.getZanrFilmu());
        //filmDTO.setPopisFilmu(film.getPopisFilmu());
        filmDTO.setObrazekFilmu(film.getObrazekFilmu());

        if(film.getPujcky()!= null){
            filmDTO.setPujcky(film.getPujcky().stream().map(Utils::mapPujckaEntituNaPujckaDTO).collect(Collectors.toList()));
        }
        return filmDTO;
    }

    public static UzivatelDTO mapUzivatelEntituNaUzivatelDTOPlusPujckaAFilm(Uzivatel uzivatel){
        UzivatelDTO uzivatelDTO = new UzivatelDTO();

        uzivatelDTO.setId(uzivatel.getId());
        uzivatelDTO.setJmeno(uzivatel.getJmeno());
        uzivatelDTO.setEmail(uzivatel.getEmail());
        uzivatelDTO.setTelCislo(uzivatel.getTelCislo());
        uzivatelDTO.setRole(uzivatel.getRole());

        if(!uzivatel.getPujcky().isEmpty()){
            uzivatelDTO.setPujcky(uzivatel.getPujcky().stream().map(pujcka -> mapPujckaEntituNaPujckaDTOPlusPujcenyFilm(pujcka,false)).collect(Collectors.toList()));
        }

        return uzivatelDTO;
    }

    public static PujckaDTO mapPujckaEntituNaPujckaDTOPlusPujcenyFilm(Pujcka pujcka, boolean mapUzivatele){
        PujckaDTO pujckaDTO = new PujckaDTO();

        pujckaDTO.setId(pujcka.getId());
        pujckaDTO.setDatumPujceni(pujcka.getDatumPujceni());
        pujckaDTO.setDatumVraceni(pujcka.getDatumVraceni());
        pujckaDTO.setPocetMomentalnePujcenychFilmu(pujcka.getPocetMomentalnePujcenychFilmu());
        pujckaDTO.setKodPotvrzeniZapujceni(pujcka.getKodPotvrzeniZapujceni());

        if(mapUzivatele){
            pujckaDTO.setUzivatelPujcuje(Utils.mapUzivatelEntituNaUzivatelDTO(pujcka.getUzivatelPujcuje()));
        }
        if(pujcka.getFilmPujceny() != null){
            FilmDTO filmDTO = new FilmDTO();

            filmDTO.setId(pujcka.getFilmPujceny().getId());
            filmDTO.setNazevFilmu(pujcka.getFilmPujceny().getNazevFilmu());
            filmDTO.setCenaFilmu(pujcka.getFilmPujceny().getCenaFilmu());
            filmDTO.setZanrFilmu(pujcka.getFilmPujceny().getZanrFilmu());
            filmDTO.setPopisFilmu(pujcka.getFilmPujceny().getPopisFilmu());
            filmDTO.setObrazekFilmu(pujcka.getFilmPujceny().getObrazekFilmu());
            pujckaDTO.setFilmPujceny(filmDTO);
        }
        return pujckaDTO;
    }

    public static List<UzivatelDTO> mapUzivatelEntituNaUzivatelListDTO(List<Uzivatel> uzivatelList){
        return uzivatelList.stream().map(Utils::mapUzivatelEntituNaUzivatelDTO).collect(Collectors.toList());
    }
    public static List<FilmDTO> mapFilmEntituNaFilmListDTO(List<Film> filmList){
        return filmList.stream().map(Utils::mapFilmEntituNaFilmDTO).collect(Collectors.toList());
    }
    public static List<PujckaDTO> mapPujckaEntituNaPujckaListDTO(List<Pujcka> pujckaList){
        return pujckaList.stream().map(Utils::mapPujckaEntituNaPujckaDTO).collect(Collectors.toList());
    }
}
