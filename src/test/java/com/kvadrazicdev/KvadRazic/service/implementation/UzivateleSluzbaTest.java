package com.kvadrazicdev.KvadRazic.service.implementation;

import com.kvadrazicdev.KvadRazic.dto.Odpoved;
import com.kvadrazicdev.KvadRazic.entity.Uzivatel;
import com.kvadrazicdev.KvadRazic.repository.UzivatelRepository;
import com.kvadrazicdev.KvadRazic.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UzivateleSluzbaTest {

    @InjectMocks
    private UzivateleSluzba uzivateleSluzba;

    @Mock
    private UzivatelRepository uzivatelRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testRejstrik_EmailUzExistuje() {
        Uzivatel uzivatel = new Uzivatel();
        uzivatel.setEmail("test@mailin.com");

        when(uzivatelRepository.existsByEmail("test@mailin.com")).thenReturn(true);

        Odpoved odpoved = uzivateleSluzba.rejstrik(uzivatel);

        assertEquals(400, odpoved.getKodStavu());
        assertEquals("test@mailin.com již existuje", odpoved.getZprava());
    }
}