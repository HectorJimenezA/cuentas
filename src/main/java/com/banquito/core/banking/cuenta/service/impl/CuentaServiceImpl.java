package com.banquito.core.banking.cuenta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banquito.core.banking.cuenta.dao.CuentaRepository;
import com.banquito.core.banking.cuenta.domain.Cuenta;
import com.banquito.core.banking.cuenta.service.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private CuentaRepository cuentaRespository;

    @Override
    public List<Cuenta> GetAll(){
        return (List<Cuenta>) cuentaRespository.findAll();
    }

}
