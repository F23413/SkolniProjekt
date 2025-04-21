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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazevFilmu() {
        return nazevFilmu;
    }

    public void setNazevFilmu(String nazevFilmu) {
        this.nazevFilmu = nazevFilmu;
    }

    public String getZanrFilmu() {
        return zanrFilmu;
    }

    public void setZanrFilmu(String zanrFilmu) {
        this.zanrFilmu = zanrFilmu;
    }

    public BigDecimal getCenaFilmu() {
        return cenaFilmu;
    }

    public void setCenaFilmu(BigDecimal cenaFilmu) {
        this.cenaFilmu = cenaFilmu;
    }

    public String getObrazekFilmu() {
        return obrazekFilmu;
    }

    public void setObrazekFilmu(String obrazekFilmu) {
        this.obrazekFilmu = obrazekFilmu;
    }

    public String getPopisFilmu() {
        return popisFilmu;
    }

    public void setPopisFilmu(String popisFilmu) {
        this.popisFilmu = popisFilmu;
    }

    public List<PujckaDTO> getPujcky() {
        return pujcky;
    }

    public void setPujcky(List<PujckaDTO> pujcky) {
        this.pujcky = pujcky;
    }
}
