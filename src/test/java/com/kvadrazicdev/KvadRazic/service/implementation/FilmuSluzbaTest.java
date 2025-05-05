package com.kvadrazicdev.KvadRazic.service.implementation;

import org.junit.jupiter.api.Test;
import com.kvadrazicdev.KvadRazic.dto.FilmDTO;
import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.entity.Film;
import com.kvadrazicdev.KvadRazic.exception.Vyjimka;
import com.kvadrazicdev.KvadRazic.repository.FilmRepository;
import com.kvadrazicdev.KvadRazic.service.AwsS3Sluzba;
import com.kvadrazicdev.KvadRazic.service.implementation.FilmuSluzba;
import com.kvadrazicdev.KvadRazic.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmuSluzbaTest {

    private FilmuSluzba filmuSluzba;
    private FilmRepository filmRepository;
    private AwsS3Sluzba awsS3Sluzba;

    @BeforeEach
    void setUp() {
        filmRepository = mock(FilmRepository.class);
        awsS3Sluzba = mock(AwsS3Sluzba.class);
        filmuSluzba = new FilmuSluzba();
        filmuSluzba.filmRepository = filmRepository;
        filmuSluzba.awsS3Sluzba = awsS3Sluzba;
    }

    @Test
    void testGetFilmDleId_success() {
        // Testovaci data
        Long filmId = 1L;
        Film film = new Film();
        film.setId(filmId);
        film.setNazevFilmu("Duna");

        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(filmId);
        filmDTO.setNazevFilmu("Duna");

        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));
        try (MockedStatic<Utils> utilsMock = mockStatic(Utils.class)) {
            utilsMock.when(() -> Utils.mapFilmEntituNaFilmDTOPlusPujcka(film)).thenReturn(filmDTO);

            Odpoved odpoved = filmuSluzba.getFilmDleId(filmId);

            assertEquals(200, odpoved.getKodStavu());
            assertEquals("Film úspěšně nalezen", odpoved.getZprava());
            assertNotNull(odpoved.getFilmPujceny());
            assertEquals("Duna", odpoved.getFilmPujceny().getNazevFilmu());
        }
    }

    @Test
    void testGetFilmDleId_filmNotFound() {
        Long filmId = 99L;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        Odpoved odpoved = filmuSluzba.getFilmDleId(filmId);

        assertEquals(404, odpoved.getKodStavu());
        assertEquals("Film nenalezen", odpoved.getZprava());
        assertNull(odpoved.getFilmPujceny());
    }

    @Test
    void testGetFilmDleId_vyjimka() {
        Long filmId = 1L;
        when(filmRepository.findById(filmId)).thenThrow(new RuntimeException("DB failure"));

        Odpoved odpoved = filmuSluzba.getFilmDleId(filmId);

        assertEquals(500, odpoved.getKodStavu());
        assertTrue(odpoved.getZprava().contains("Nastala chyba během hledání filmu"));
        assertNull(odpoved.getFilmPujceny());
    }
}