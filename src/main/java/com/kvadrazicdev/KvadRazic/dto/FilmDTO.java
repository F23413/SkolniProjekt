package com.kvadrazicdev.KvadRazic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kvadrazicdev.KvadRazic.entity.Pujcka;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDTO {
    private Long id;
    private String nazevFilmu;
    private String zanrFilmu;
    private BigDecimal cenaFilmu;
    private String obrazekFilmu;
    private String popisFilmu;
    private List<PujckaDTO> pujcky;

}
