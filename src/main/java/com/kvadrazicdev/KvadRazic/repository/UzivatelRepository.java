package com.kvadrazicdev.KvadRazic.repository;

import com.kvadrazicdev.KvadRazic.entity.Uzivatel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UzivatelRepository extends JpaRepository<Uzivatel, Long> {
    boolean existujeDleEmail(String Email);
    Optional<Uzivatel> najdiDleEmail(String Email);
}
