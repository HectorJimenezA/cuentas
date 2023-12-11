package com.banquito.core.banking.cuenta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.banking.cuenta.domain.TipoCuenta;
import com.banquito.core.banking.cuenta.service.TipoCuentaService;

@RestController
@RequestMapping("tipocuenta")

public class TipoCuentaController {
    @Autowired
    private TipoCuentaService tipoCuentaService;

    @GetMapping("getall")
    public List<TipoCuenta> GetAll(){
        return tipoCuentaService.GetAll();
    }
}
