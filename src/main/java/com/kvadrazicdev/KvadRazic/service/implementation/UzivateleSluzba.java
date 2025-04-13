package com.kvadrazicdev.KvadRazic.service.implementation;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.dto.UzivatelDTO;
import com.kvadrazicdev.KvadRazic.dto.ZadostPrihlaseni;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;
import com.kvadrazicdev.KvadRazic.exception.Vyjimka;
import com.kvadrazicdev.KvadRazic.repository.UzivatelRepository;
import com.kvadrazicdev.KvadRazic.service.interfate.IUzivateleSluzba;
import com.kvadrazicdev.KvadRazic.utils.JWTUtils;
import com.kvadrazicdev.KvadRazic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UzivateleSluzba implements IUzivateleSluzba {
@Autowired
private UzivatelRepository uzivatelRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Odpoved rejstrik(Uzivatel uzivatel) {
        Odpoved odpoved = new Odpoved();
        try {
            if(uzivatel.getRole() == null || uzivatel.getRole().isBlank()){
                uzivatel.setRole("UZIVATEL");
            }
            if(uzivatelRepository.existsByEmail(uzivatel.getEmail())){
                throw new Vyjimka(uzivatel.getEmail() + " již existuje");
            }
            uzivatel.setHeslo(passwordEncoder.encode(uzivatel.getPassword()));
            Uzivatel ulozenyUzivatel = uzivatelRepository.save(uzivatel);
            UzivatelDTO uzivatelDTO = Utils.mapUzivatelEntituNaUzivatelDTO(ulozenyUzivatel);
            odpoved.setKodStavu(200);
            odpoved.setUzivatelPujcuje(uzivatelDTO);
        }catch (Vyjimka e){
            odpoved.setKodStavu(400); // bad reqest
            odpoved.setZprava(e.getMessage());
        }
        catch (Exception e){

            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během registrace uživatele " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved prihlaseni(ZadostPrihlaseni zadostPrihlaseni) {
        Odpoved odpoved = new Odpoved();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(zadostPrihlaseni.getEmail(), zadostPrihlaseni.getHeslo()));
            var uzivatel = uzivatelRepository.findByEmail(zadostPrihlaseni.getEmail()).orElseThrow(()-> new Vyjimka("Uživatel nebyl nalezen"));
            var token = jwtUtils.generatorTokenu(uzivatel);
            odpoved.setKodStavu(200);
            odpoved.setToken(token);
            odpoved.setRole(uzivatel.getRole());
            odpoved.setCasExpirace("7 dnů");
            odpoved.setZprava("přihlášení proběhlo úspěšně");
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());
        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba během přihlašování uživatele " + e.getMessage());

        }
        return odpoved;
    }

    @Override
    public Odpoved getVsechnyUzivatele() {
        Odpoved odpoved = new Odpoved();
        try {
            List<Uzivatel> uzivatelList = uzivatelRepository.findAll();
            List<UzivatelDTO> uzivatelDTOList = Utils.mapUzivatelEntituNaUzivatelListDTO(uzivatelList);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Akce proběhla úspěšně");
            odpoved.setSeznamUzivatelu(uzivatelDTOList);

        }catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba při hledání uživatelů " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved getHistoriePujcovaniUzivatele(String uzivatelId) {
        Odpoved odpoved = new Odpoved();
        try {
            Uzivatel uzivatel = uzivatelRepository.findById(Long.valueOf(uzivatelId)).orElseThrow(()-> new Vyjimka("Uživatel nebyl nalezen"));
            UzivatelDTO uzivatelDTO = Utils.mapUzivatelEntituNaUzivatelDTOPlusPujckaAFilm(uzivatel);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Akce proběhla úspěšně");
            odpoved.setUzivatelPujcuje(uzivatelDTO);
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());}
        catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba při hledání historie " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved odstranUzivatele(String uzivatelId) {
        Odpoved odpoved = new Odpoved();
        try {
            uzivatelRepository.findById(Long.valueOf(uzivatelId)).orElseThrow(()-> new Vyjimka("Uživatel nebyl nalezen"));
            uzivatelRepository.deleteById(Long.valueOf(uzivatelId));
            odpoved.setKodStavu(200);
            odpoved.setZprava("Uživatel úspěšně odstraněn");
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());}
        catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba při odstraňování uživatele " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved getUzivateleById(String uzivatelId) {
        Odpoved odpoved = new Odpoved();
        try {
            Uzivatel uzivatel = uzivatelRepository.findById(Long.valueOf(uzivatelId)).orElseThrow(()-> new Vyjimka("Uživatel nebyl nalezen"));
            UzivatelDTO uzivatelDTO = Utils.mapUzivatelEntituNaUzivatelDTO(uzivatel);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Akce proběhla úspěšně");
            odpoved.setUzivatelPujcuje(uzivatelDTO);
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());}
        catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba při hledání historie " + e.getMessage());
        }
        return odpoved;
    }

    @Override
    public Odpoved getMojeInfo(String email) {
        Odpoved odpoved = new Odpoved();
        try {
            Uzivatel uzivatel = uzivatelRepository.findByEmail(email).orElseThrow(()-> new Vyjimka("Uživatel nebyl nalezen"));
            UzivatelDTO uzivatelDTO = Utils.mapUzivatelEntituNaUzivatelDTO(uzivatel);
            odpoved.setKodStavu(200);
            odpoved.setZprava("Akce proběhla úspěšně");
            odpoved.setUzivatelPujcuje(uzivatelDTO);
        }catch (Vyjimka e){
            odpoved.setKodStavu(404); // not found
            odpoved.setZprava(e.getMessage());}
        catch (Exception e){
            odpoved.setKodStavu(500); // server error
            odpoved.setZprava("Nastala chyba při hledání historie " + e.getMessage());
        }
        return odpoved;
    }
}
