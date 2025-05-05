package com.kvadrazicdev.KvadRazic.service.implementation;

import org.junit.jupiter.api.Test;
import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.dto.PujckaDTO;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import com.kvadrazicdev.KvadRazic.exception.Vyjimka;
import com.kvadrazicdev.KvadRazic.repository.PujckaRepository;
import com.kvadrazicdev.KvadRazic.service.implementation.PujckySluzba;
import com.kvadrazicdev.KvadRazic.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;

class PujckySluzbaTest {
    @Mock
    private PujckaRepository pujckaRepository;

    @InjectMocks
    private PujckySluzba pujckySluzba;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testFindPujckuDleKodPotvrzeni_success() {
        String kod = "ABC123XYZ";
        Pujcka pujcka = new Pujcka();
        pujcka.setKodPotvrzeniZapujceni(kod);

        PujckaDTO pujckaDTO = new PujckaDTO();
        pujckaDTO.setKodPotvrzeniZapujceni(kod);

        when(pujckaRepository.findByKodPotvrzeniZapujceni(kod)).thenReturn(Optional.of(pujcka));
        mockStatic(Utils.class).when(() -> Utils.mapPujckaEntituNaPujckaDTOPlusPujcenyFilm(pujcka, true)).thenReturn(pujckaDTO);

        Odpoved odpoved = pujckySluzba.findPujckuDleKodPotvrzeni(kod);

        assertEquals(200, odpoved.getKodStavu());
        assertEquals("Půjčky úspěšně nalezeny", odpoved.getZprava());
        assertNotNull(odpoved.getOPujcce());
        assertEquals(kod, odpoved.getOPujcce().getKodPotvrzeniZapujceni());

        verify(pujckaRepository, times(1)).findByKodPotvrzeniZapujceni(kod);
    }

    @Test
    void testFindPujckuDleKodPotvrzeni_notFound() {
        String kod = "NONEXISTENT";
        when(pujckaRepository.findByKodPotvrzeniZapujceni(kod)).thenReturn(Optional.empty());

        Odpoved odpoved = pujckySluzba.findPujckuDleKodPotvrzeni(kod);

        assertEquals(404, odpoved.getKodStavu());
        assertEquals("Pujcka nebyla nalezena", odpoved.getZprava());

        verify(pujckaRepository, times(1)).findByKodPotvrzeniZapujceni(kod);
    }
}