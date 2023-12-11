package com.banquito.core.banking.cuenta.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transaccion")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_transaccion", nullable = false)
    private Integer codTransaccion;

    @Column(name = "cod_unico", nullable = false, length = 64)
    private String codUnico;

    @Column(name = "tipo_afectacion", nullable = false, length = 1)
    private String tipoAfectacion;

    @Column(name = "valor_debe", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorDebe;

    @Column(name = "valor_haber", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorHaber;

    @Column(name = "tipo_transaccion", nullable = false, length = 8)
    private String tipoTransaccion;

    @Column(name = "detalle", nullable = false, length = 50)
    private String detalle;

    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaCreacion;

    @Column(name = "estado", nullable = false, length = 3)
    private String estado;

    @Column(name = "fecha_afectacion", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaAfectacion;

    @Version
    private Long version;

    @ManyToOne()
    @JoinColumn(name = "cod_cuenta", nullable = false, insertable = false, updatable = false)
    private Cuenta cuenta;

    public Transaccion() {
    }

    public Transaccion(Integer codTransaccion) {
        this.codTransaccion = codTransaccion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codTransaccion == null) ? 0 : codTransaccion.hashCode());
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
        Transaccion other = (Transaccion) obj;
        if (codTransaccion == null) {
            if (other.codTransaccion != null)
                return false;
        } else if (!codTransaccion.equals(other.codTransaccion))
            return false;
        return true;
    }

    
}
