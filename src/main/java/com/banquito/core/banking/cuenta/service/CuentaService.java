package com.banquito.core.banking.cuenta.service;

import java.util.List;

import com.banquito.core.banking.cuenta.domain.Cuenta;

public interface CuentaService {

    List<Cuenta> GetAll();
    
}
