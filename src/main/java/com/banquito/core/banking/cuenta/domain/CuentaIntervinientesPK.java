package com.banquito.core.banking.cuenta.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class CuentaIntervinientesPK implements Serializable {
    @Column(name = "cod_cuenta", nullable = false)
    private Integer codCuenta;

    @Column(name = "cod_cliente_persona", nullable = false)
    private Integer codClientePersona;

    public CuentaIntervinientesPK() {
    }

    public CuentaIntervinientesPK(Integer codCuenta, Integer codClientePersona) {
        this.codCuenta = codCuenta;
        this.codClientePersona = codClientePersona;
    }
    
    
}
