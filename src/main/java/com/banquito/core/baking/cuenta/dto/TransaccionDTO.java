package com.banquito.core.baking.cuenta.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransaccionDTO {

    private Integer codCuentaOrigen;
    private Integer codCuentaDestino;
    private String codUnico;
    private String tipoAfectacion;
    private BigDecimal valorDebe;
    private BigDecimal valorHaber;
    private String tipoTransaccion;
    private String detalle;
    private Timestamp fechaCreacion;
    private String estado;
    private Timestamp fechaAfectacion;
    private Timestamp fechaUltimoCambio;

    @Override
    public String toString() {
        return "TransaccionDTO [ codCuentaOrigen=" + codCuentaOrigen
                + ", codCuentaDestino=" + codCuentaDestino + ", codUnico=" + codUnico + ", tipoAfectacion="
                + tipoAfectacion + ", valorDebe=" + valorDebe + ", valorHaber=" + valorHaber + ", tipoTransaccion="
                + tipoTransaccion + ", detalle=" + detalle + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado
                + ", fechaAfectacion=" + fechaAfectacion + ", fechaUltimoCambio=" + fechaUltimoCambio + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransaccionDTO other = (TransaccionDTO) obj;
        if (codUnico == null) {
            if (other.codUnico != null)
                return false;
        } else if (!codUnico.equals(other.codUnico))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codUnico == null) ? 0 : codUnico.hashCode());
        return result;
    }

    
    
}
