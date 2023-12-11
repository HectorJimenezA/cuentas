package com.banquito.core.banking.cuenta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banquito.core.banking.cuenta.dao.TipoCuentaRepository;
import com.banquito.core.banking.cuenta.domain.TipoCuenta;
import com.banquito.core.banking.cuenta.service.TipoCuentaService;

@Service
public class TipoCuentaServiceImpl implements TipoCuentaService {
    @Autowired
    private TipoCuentaRepository tipoCuentaRepository;

    @Override
    public List<TipoCuenta> GetAll() {
        return (List<TipoCuenta>) tipoCuentaRepository.findAll();
    }
}
