package com.banquito.core.banking.cuenta.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.banquito.core.banking.cuenta.domain.Transaccion;

public interface TransaccionService {
    List<Transaccion> GetAll();
    Optional<Transaccion> GetById(Integer id);
    List <Transaccion> findByFechaCreacion(Timestamp fechaCreacion);
}
