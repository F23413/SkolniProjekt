package com.kvadrazicdev.KvadRazic.service;

import com.kvadrazicdev.KvadRazic.exception.Vyjimka;
import com.kvadrazicdev.KvadRazic.repository.UzivatelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VlastniUserDetailsService implements UserDetailsService {
    @Autowired
    private UzivatelRepository uzivatelRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return uzivatelRepository.findByEmail(username).orElseThrow(()->new Vyjimka("Nebylo nalezeno uživatelské jméno nebo email."));
    }
}
