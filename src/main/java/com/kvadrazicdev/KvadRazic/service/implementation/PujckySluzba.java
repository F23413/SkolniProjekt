package com.kvadrazicdev.KvadRazic.service.implementation;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.dto.PujckaDTO;
import com.kvadrazicdev.KvadRazic.entity.Film;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;
import com.kvadrazicdev.KvadRazic.exception.Vyjimka;
import com.kvadrazicdev.KvadRazic.repository.FilmRepository;
import com.kvadrazicdev.KvadRazic.repository.PujckaRepository;
import com.kvadrazicdev.KvadRazic.repository.UzivatelRepository;
import com.kvadrazicdev.KvadRazic.service.interfate.IFilmuSluzba;
import com.kvadrazicdev.KvadRazic.service.interfate.IPujckySluzba;
import com.kvadrazicdev.KvadRazic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PujckySluzba implements IPujckySluzba {
    @Autowired
    private PujckaRepository pujckaRepository;
    @Autowired
    private IFilmuSluzba filmuSluzba;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private UzivatelRepository uzivatelRepository;
    @Override
    public Odpoved ulozPujcku(Long filmId, Long uzivatelId, Pujcka zadostPujcky) {
        Odpoved odpoved = new Odpoved();
        try{
            if(zadostPujcky.getDatumVraceni().isBefore(zadostPujcky.getDatumPujceni())){
                throw new IllegalArgumentException("Datum půjčení musí být pozdější než datum vrácení");
            }
            Film film = filmRepository.findById(filmId).orElseThrow(()-> new Vyjimka("Film nebyl nalezen"));
            Uzivatel uzivatel = uzivatelRepository.findById(uzivatelId).orElseThrow(()-> new Vyjimka("Uživatel nebyl nalezen"));

            List<Pujcka> existujiciPujcky = film.getPujcky();
            
            if(!filmJeVolny(zadostPujcky, existujiciPujcky)){
                throw new Vyjimka("Film není k mání pro vybrané datum");
            }

            zadostPujcky.setFilmPujceny(film);
            zadostPujcky.setUzivatelPujcuje(uzivatel);
            String kodPotvrzeniZapujceni = Utils.generujRandomAlfanumer(10);
            zadostPujcky.setKodPotvrzeniZapujceni(kodPotvrzeniZapujceni);
            pujckaRepository.save(zadostPujcky);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Film úspěšně zapůjčen");
            odpoved.setKodPotvrzeniZapujceni(kodPotvrzeniZapujceni);

        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během ukládání zapůjčení: " + e.getMessage());
        }
        return odpoved;
    }

    private boolean filmJeVolny(Pujcka zadostPujcky, List<Pujcka> existujiciPujcky) {
        return existujiciPujcky.stream()
                .noneMatch(existujiciPujcka ->
                        zadostPujcky.getDatumPujceni().equals(existujiciPujcka.getDatumPujceni())
                        || zadostPujcky.getDatumVraceni().isBefore(existujiciPujcka.getDatumVraceni())
                        || (zadostPujcky.getDatumPujceni().isAfter(existujiciPujcka.getDatumPujceni())
                        && zadostPujcky.getDatumPujceni().isBefore(existujiciPujcka.getDatumVraceni()))
                        || (zadostPujcky.getDatumPujceni().isBefore(existujiciPujcka.getDatumPujceni())

                        && zadostPujcky.getDatumVraceni().equals(existujiciPujcka.getDatumVraceni()))
                        || (zadostPujcky.getDatumPujceni().isBefore(existujiciPujcka.getDatumPujceni())

                        && zadostPujcky.getDatumVraceni().isAfter(existujiciPujcka.getDatumVraceni()))

                        || (zadostPujcky.getDatumPujceni().equals(existujiciPujcka.getDatumVraceni())
                        && zadostPujcky.getDatumVraceni().equals(existujiciPujcka.getDatumPujceni()))

                        || (zadostPujcky.getDatumPujceni().equals(existujiciPujcka.getDatumVraceni())
                        && zadostPujcky.getDatumVraceni().equals(zadostPujcky.getDatumPujceni()))
                );
    }

    @Override
    public Odpoved findPujckuDleKodPotvrzeni(String kodPotvrzeniZapujceni) {
        Odpoved odpoved = new Odpoved();
        try{
            Pujcka pujcka = pujckaRepository.findByKodPotvrzeniZapujceni(kodPotvrzeniZapujceni).orElseThrow(()-> new Vyjimka("Pujcka nebyla nalezena"));
            PujckaDTO pujckaDTO = Utils.mapPujckaEntituNaPujckaDTO(pujcka);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Půjčky úspěšně nalezeny");
            odpoved.setOPujcce(pujckaDTO);

        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během hledání půjček: " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved getVsechnyPujcky() {
        Odpoved odpoved = new Odpoved();
        try{
            List<Pujcka> pujckaList = pujckaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<PujckaDTO> pujckaDTOList = Utils.mapPujckaEntituNaPujckaListDTO(pujckaList);

            odpoved.setKodStavu(200);
            odpoved.setZprava("Půjčky úspěšně nalezeny");
            odpoved.setSeznamPujcek(pujckaDTOList);

        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během hledání půjček: " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved zrusPujcku(Long pujckaId) {
        Odpoved odpoved = new Odpoved();
        try{
            pujckaRepository.findById(pujckaId).orElseThrow(()-> new Vyjimka("Půjčka nebyla nalezena"));
            pujckaRepository.deleteById(pujckaId);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Půjčka úspěšně zrušena");
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během pokusu o zrušení zapůjčení: " + e.getMessage());
        }
        return odpoved;
    }
}
