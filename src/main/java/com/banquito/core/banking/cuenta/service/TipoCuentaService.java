package com.banquito.core.banking.cuenta.service;

import java.util.List;

import com.banquito.core.banking.cuenta.domain.TipoCuenta;

public interface TipoCuentaService {
    
    List<TipoCuenta> GetAll();
}
