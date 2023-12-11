package com.banquito.core.banking.cuenta.controller;

import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.banking.cuenta.domain.Cuenta;
import com.banquito.core.banking.cuenta.service.CuentaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cuenta")

public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/getall")
    public List<Cuenta> GetAll(){
        return cuentaService.GetAll();
    }
    
    @GetMapping("holamundo")
    public String GetAl(){
        return "cuentaService.GetAll()";
    }
}
