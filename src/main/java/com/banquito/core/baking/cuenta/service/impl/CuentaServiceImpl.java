package com.banquito.core.baking.cuenta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.banquito.core.baking.cuenta.service.CuentaService;
import com.banquito.core.baking.cuenta.dao.CuentaRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private CuentaRepository cuentaRespository;

    @Override
    public List<Cuenta> GetAll(){
        return (List<Cuenta>) cuentaRespository.findAll();
    }

}
