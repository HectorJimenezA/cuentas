package com.banquito.core.baking.cuenta.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_cuenta", nullable = false)
    private Integer codCuenta;

    @Column(name = "numero_cuenta", nullable = false, length = 8)
    private String numeroCuenta;

    @Column(name = "saldo_contable", nullable = false, precision = 18, scale = 2)
    private BigDecimal saldoContable;

    @Column(name = "saldo_disponible", nullable = false, precision = 18, scale = 2)
    private BigDecimal saldoDisponible;

    @Column(name = "estado", nullable = false, length = 3)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaCreacion;

    @Column(name = "fecha_ultimo_cambio", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaUltimoCambio;

    // @ManyToOne()
    // @JoinColumn(name = "COD_TIPO_CUENTA", nullable = false, updatable = false, insertable = false)
    // private TipoCuenta tipoCuenta;

    // @OneToMany()
    // private List <Transaccion> transaccion;

    // @ManyToOne()
    // @JoinColumn(name = "COD_CLIENTE", nullable = false, updatable = false, insertable = false)
    // private Cliente cliente;

    public Cuenta() {
    }

    public Cuenta(Integer codCuenta) {
        this.codCuenta = codCuenta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codCuenta == null) ? 0 : codCuenta.hashCode());
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
        Cuenta other = (Cuenta) obj;
        if (codCuenta == null) {
            if (other.codCuenta != null)
                return false;
        } else if (!codCuenta.equals(other.codCuenta))
            return false;
        return true;
    }
    

}
