package com.banquito.core.banking.cuenta.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banquito.core.banking.cuenta.dao.TransaccionRepository;
import com.banquito.core.banking.cuenta.domain.Transaccion;
import com.banquito.core.banking.cuenta.service.TransaccionService;

@Service
public class TransaccionServiceImpl implements TransaccionService {
    @Autowired
    private TransaccionRepository transaccionRepository;

    @Override
    public List<Transaccion> GetAll() {
        return (List<Transaccion>) transaccionRepository.findAll();
    }
    @Override
    public Optional<Transaccion> GetById(Integer id) {
        return transaccionRepository.findById(id);
    }
    @Override
    public List<Transaccion> findByFechaCreacion(Timestamp fechaCreacion) {
        return (List<Transaccion>) transaccionRepository/* .findByFechaCreacion(fechaCreacion)*/;
    }
}
