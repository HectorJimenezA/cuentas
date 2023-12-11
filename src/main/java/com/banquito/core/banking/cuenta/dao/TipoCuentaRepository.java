package com.banquito.core.banking.cuenta.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.banking.cuenta.domain.TipoCuenta;

@Repository
public interface TipoCuentaRepository extends CrudRepository<TipoCuenta, String>{
    List<TipoCuenta> findByTipoCapitalizacionAndFechaCreacion(String tipoCapitalizacion, Timestamp fechaCreacion);
    
}
