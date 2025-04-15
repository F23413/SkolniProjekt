package com.kvadrazicdev.KvadRazic.service.implementation;

import com.kvadrazicdev.KvadRazic.dto.FilmDTO;
import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.entity.Film;
import com.kvadrazicdev.KvadRazic.exception.Vyjimka;
import com.kvadrazicdev.KvadRazic.repository.FilmRepository;
import com.kvadrazicdev.KvadRazic.repository.PujckaRepository;
import com.kvadrazicdev.KvadRazic.service.AwsS3Sluzba;
import com.kvadrazicdev.KvadRazic.service.interfate.IFilmuSluzba;
import com.kvadrazicdev.KvadRazic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FilmuSluzba implements IFilmuSluzba {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PujckaRepository pujckaRepository;
    @Autowired
    private AwsS3Sluzba awsS3Sluzba;
    @Override
    public Odpoved pridejNovyFilm(MultipartFile obrazekFilmu, String zanrFilmu, BigDecimal cenaFilmu, String popisFilmu) {
        Odpoved odpoved = new Odpoved();

        try{
            String obrazekUrl = awsS3Sluzba.ulozImgDoS3(obrazekFilmu);
            Film film = new Film();
            film.setObrazekFilmu(obrazekUrl);
            film.setZanrFilmu(zanrFilmu);
            film.setCenaFilmu(cenaFilmu);
            film.setPopisFilmu(popisFilmu);
            Film ulozenyFilm = filmRepository.save(film);
            FilmDTO filmDTO = Utils.mapFilmEntituNaFilmDTO(ulozenyFilm);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně uložen");
            odpoved.setFilmPujceny(filmDTO);
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během přidávání filmu: " + e.getMessage());
        }
        return odpoved;
    }


    @Override
    public List<String> getVsechnyZanry() {
        return filmRepository.najdiDleZanru();
    }

    @Override
    public Odpoved getVsechnyFilmy() {
        Odpoved odpoved = new Odpoved();

        try{
            List<Film> filmList = filmRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<FilmDTO> filmDTOList = Utils.mapFilmEntituNaFilmListDTO(filmList);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně uložen");
            odpoved.setSeznamFilmu(filmDTOList);
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během hledání filmů: " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved odstranFilm(Long filmId) {
        Odpoved odpoved = new Odpoved();

        try{
            filmRepository.findById(filmId).orElseThrow(()-> new Vyjimka("Film nenalezen"));
            filmRepository.deleteById(filmId);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně odstraněn");
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během odstraňování filmu: " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved updateFilm(Long filmId, String zanrFilmu, BigDecimal cenaFilmu, MultipartFile obrazekFilmu, String popisFilmu) {
        Odpoved odpoved = new Odpoved();
        try{
            String obrazekUrl = null;
            if(obrazekFilmu != null && !obrazekFilmu.isEmpty()){
                obrazekUrl = awsS3Sluzba.ulozImgDoS3(obrazekFilmu);
            }
            Film film = filmRepository.findById(filmId).orElseThrow(()-> new Vyjimka("Film nenalezen"));
            if(zanrFilmu != null) film.setZanrFilmu(zanrFilmu);
            if(popisFilmu != null) film.setPopisFilmu(popisFilmu);
            if(cenaFilmu != null) film.setCenaFilmu(cenaFilmu);
            if(obrazekUrl != null) film.setObrazekFilmu(obrazekUrl);

            Film updatedFilm = filmRepository.save(film);
            FilmDTO filmDTO = Utils.mapFilmEntituNaFilmDTO(updatedFilm);

            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně odstraněn");
            odpoved.setFilmPujceny(filmDTO);
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během ukládání změn filmu: " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved getFilmDleId(Long filmId) {
        Odpoved odpoved = new Odpoved();
        try{
            Film film = filmRepository.findById(filmId).orElseThrow(()-> new Vyjimka("Film nenalezen"));
            FilmDTO filmDTO = Utils.mapFilmEntituNaFilmDTOPlusPujcka(film);

            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně nalezen");
            odpoved.setFilmPujceny(filmDTO);
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během hledání filmu: " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved getMozneFilmyDleDataAZanru(LocalDate datumPujceni, LocalDate datumVraceni, String zanrFilmu) {
        Odpoved odpoved = new Odpoved();
        try{
            List<Film> mozneFilmyList = filmRepository.najdiDostupneFilmyDleDataAZanru(datumPujceni, datumVraceni, zanrFilmu);
            List<FilmDTO> filmDTOList = Utils.mapFilmEntituNaFilmListDTO(mozneFilmyList);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně nalezen");
            odpoved.setSeznamFilmu(filmDTOList);
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během hledání filmu: " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved getVsechnyMozneFilmy() {
        Odpoved odpoved = new Odpoved();
        try{
            List<Film> filmList = filmRepository.vratDostupneFilmy();
            List<FilmDTO> filmDTOList = Utils.mapFilmEntituNaFilmListDTO(filmList);


            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně nalezen");
            odpoved.setSeznamFilmu(filmDTOList);
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během hledání filmů: " + e.getMessage());
        }
        return odpoved;
    }
}
