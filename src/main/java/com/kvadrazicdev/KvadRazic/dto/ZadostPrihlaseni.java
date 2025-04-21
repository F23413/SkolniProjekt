package com.kvadrazicdev.KvadRazic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ZadostPrihlaseni {
    @NotBlank(message = "Email je nezbytný")
    private String email;
    @NotBlank(message = "Heslo je nezbytné")
    private String heslo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }
}
