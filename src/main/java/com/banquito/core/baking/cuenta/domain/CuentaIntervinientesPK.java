package com.banquito.core.baking.cuenta.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class CuentaIntervinientesPK implements Serializable {
    @Column(name = "COD_CUENTA", nullable = false)
    private Integer codCuenta;

    @Column(name = "COD_CLIENTE_PERSONA", nullable = false)
    private String codClientePersona;

    public CuentaIntervinientesPK() {
    }

    public CuentaIntervinientesPK(Integer codCuenta, String codClientePersona) {
        this.codCuenta = codCuenta;
        this.codClientePersona = codClientePersona;
    }

    @Override
    public String toString() {
        return "CuentaIntervinientesPK [codCuenta=" + codCuenta + ", codClientePersona=" + codClientePersona + "]";
    }

    
    
    
}
