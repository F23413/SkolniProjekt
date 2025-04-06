package com.kvadrazicdev.KvadRazic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ZadostPrihlaseni {
    @NotBlank(message = "Email je nezbytný")
    private String Email;
    @NotBlank(message = "Heslo je nezbytné")
    private String Heslo;
}
