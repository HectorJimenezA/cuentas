package com.banquito.core.baking.cuenta.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.google.auto.value.AutoValue.Builder;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
@Data
public class TransaccionDTO {

    private Integer codTransaccion;
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
        return "TransaccionDTO [codTransaccion=" + codTransaccion + ", codCuentaOrigen=" + codCuentaOrigen
                + ", codCuentaDestino=" + codCuentaDestino + ", codUnico=" + codUnico + ", tipoAfectacion="
                + tipoAfectacion + ", valorDebe=" + valorDebe + ", valorHaber=" + valorHaber + ", tipoTransaccion="
                + tipoTransaccion + ", detalle=" + detalle + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado
                + ", fechaAfectacion=" + fechaAfectacion + ", fechaUltimoCambio=" + fechaUltimoCambio + "]";
    }

    
}
