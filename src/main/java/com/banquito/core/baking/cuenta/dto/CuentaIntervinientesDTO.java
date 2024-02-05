package com.banquito.core.baking.cuenta.dto;

import com.google.auto.value.AutoValue.Builder;

import io.opencensus.common.Timestamp;
import lombok.Data;

@Builder
@Data
public class CuentaIntervinientesDTO {

    private CuentaIntervinientesPKDTO pk;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String estado;
    private Timestamp fechaUltimoCambio;

    

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CuentaIntervinienteDTO [pk=" + pk + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
                + ", estado=" + estado + ", fechaUltimoCambio=" + fechaUltimoCambio + "]";
    }

    

}
