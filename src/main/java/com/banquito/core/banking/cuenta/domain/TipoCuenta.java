package com.banquito.core.banking.cuenta.domain;

import jakarta.persistence.Entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Getter
@Setter
@Entity
@Table(name = "tipo_cuenta")
public class TipoCuenta {
    @Id
    @Column(name = "cod_tipo_cuenta", nullable = false, length = 10)
    private String codTipoCuenta;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;
    @Column(name = "tipo_capitalizacion", nullable = false, length = 3)
    private String tipoCapitalizacion;
    @Column(name = "forma_capitalizacion", nullable = false, length = 3)
    private String formaCapitalizacion;
    @Column(name = "maximo_numero_intervinientes", nullable = false, length = 2)
    private Integer maximoNumeroIntervinientes;

    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaCreacion;

    @Version
    private Long version;

    @OneToMany(mappedBy = "tipoCuenta")
    private List<Cuenta> cuenta;


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
