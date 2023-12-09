package com.banquito.core.baking.cuenta.domain;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Setter;

@Setter
@Entity
@Table(name = "CUENTA_INTERVINIENTES")
public class CuentaIntervinientes {
    @EmbeddedId
    private CuentaIntervinientesPK PK;

    @Column(name = "FECHA_INICIO", nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaInicio;

     @Column(name = "FECHA_FIN", nullable = true )
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaFin;

    @Column(name = "ESTADO", nullable = false, length = 3)
    private String estado;

    

    public CuentaIntervinientes() {
    }

    public CuentaIntervinientes(CuentaIntervinientesPK pK) {
        PK = pK;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((PK == null) ? 0 : PK.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CuentaIntervinientes other = (CuentaIntervinientes) obj;
        if (PK == null) {
            if (other.PK != null)
                return false;
        } else if (!PK.equals(other.PK))
            return false;
        return true;
    }

    

    
}
