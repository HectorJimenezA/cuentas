package com.banquito.core.banking.cuenta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.banking.cuenta.domain.CuentaIntervinientes;
import com.banquito.core.banking.cuenta.service.CuentaIntervinientesService;

@RestController
@RequestMapping("cuentaintervinientes")
public class CuentaIntervinientesController {
    @Autowired
    private CuentaIntervinientesService cuentaIntervinientesService;

    @GetMapping("getall")
    public List<CuentaIntervinientes> GetAll() {
        return cuentaIntervinientesService.GetAll();
    }
}
