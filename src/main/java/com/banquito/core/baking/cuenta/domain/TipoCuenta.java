package com.banquito.core.baking.cuenta.domain;

import jakarta.persistence.Entity;

import java.security.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Setter
@Entity
@Table(name = "TIPO_CUENTA")
public class TipoCuenta {
    @Id
    @Column(name = "COD_TIPO_CUENTA", nullable = false, length = 10)
    private String codTipoCuenta;
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;
    @Column(name = "DESCRIPCION", nullable = false, length = 500)
    private String descripcion;
    @Column(name = "TIPO_CAPITALIZACION", nullable = false, length = 3)
    private String tipoCapitalizacion;
    @Column(name = "FORMA_CAPITALIZACION", nullable = false, length = 3)
    private String formaCapitalizacion;
    @Column(name = "MAXIMO_NUMERO_INTERVINIENTES", nullable = false, length = 2)
    private Integer numeroIntervinientes;

    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaCreacion;

    // @OneToMany(mappedBy = "tipoCuenta")
    // private List<Cuenta> cuenta;

    // @ManyToOne()
    // @JoinColumn(name = "COD_TASA_INTERES",insertable = false, updatable = false)
    // private TasaInteres tasaIntereses;

    public TipoCuenta() {
    }

    public TipoCuenta(String codTipoCuenta) {
        this.codTipoCuenta = codTipoCuenta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codTipoCuenta == null) ? 0 : codTipoCuenta.hashCode());
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
        TipoCuenta other = (TipoCuenta) obj;
        if (codTipoCuenta == null) {
            if (other.codTipoCuenta != null)
                return false;
        } else if (!codTipoCuenta.equals(other.codTipoCuenta))
            return false;
        return true;
    }

    
    

}
