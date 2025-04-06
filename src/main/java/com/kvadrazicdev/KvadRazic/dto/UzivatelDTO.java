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
    private Long id;
    private String jmeno;
    private String email;
    private String telCislo;
    private String role;
    private List<PujckaDTO> pujcky = new ArrayList<>();
}
