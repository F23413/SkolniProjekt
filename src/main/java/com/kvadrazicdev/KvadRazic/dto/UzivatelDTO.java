package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UzivatelDTO {
    private Long Id;
    private String Jmeno;
    private String Email;
    private String TelCislo;
    private String Role;
    private List<Pujcka> Pujcky = new ArrayList<>();
}
