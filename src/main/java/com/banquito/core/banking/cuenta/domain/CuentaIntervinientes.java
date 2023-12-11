package com.banquito.core.banking.cuenta.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuenta_intervinientes")
public class CuentaIntervinientes {
    @EmbeddedId
    private CuentaIntervinientesPK PK;

    @Column(name = "fecha_inicio", nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaInicio;

     @Column(name = "fecha_fin", nullable = true )
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaFin;

    @Column(name = "estado", nullable = false, length = 3)
    private String estado;

    @Version
    private Long version;


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
