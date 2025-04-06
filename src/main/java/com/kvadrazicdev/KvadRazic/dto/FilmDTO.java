package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDTO {
    private String NazevFilmu;
    private String ZanrFilmu;
    private BigDecimal CenaFilmu;
    private String ObrazekFilmu;
    private String PopisFilmu;
    private List<Pujcka> Pujcky;

}
