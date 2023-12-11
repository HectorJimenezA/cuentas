package com.banquito.core.banking.cuenta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banquito.core.banking.cuenta.dao.CuentaIntervinientesRepository;
import com.banquito.core.banking.cuenta.domain.CuentaIntervinientes;
import com.banquito.core.banking.cuenta.service.CuentaIntervinientesService;

@Service
public class CuentaIntervinientesServiceImpl implements CuentaIntervinientesService {
    @Autowired
    private CuentaIntervinientesRepository cuentaIntervinientesRespository;

    @Override
    public List<CuentaIntervinientes> GetAll() {
        return (List<CuentaIntervinientes>) cuentaIntervinientesRespository.findAll();
    }
}
