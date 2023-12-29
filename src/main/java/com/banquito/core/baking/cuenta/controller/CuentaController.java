package com.banquito.core.baking.cuenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.service.CuentaService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/cuenta")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Cuenta> GetById(@PathVariable("id") Integer id) {
        return cuentaService.getById(id)
                .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Cuenta> Save(@RequestBody Cuenta cuenta) {
        return new ResponseEntity<>(cuentaService.create(cuenta), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> Delete(@PathVariable Integer id) {
        cuentaService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Cuenta> Update(@RequestBody Cuenta credito) {
        return new ResponseEntity<>(cuentaService.update(credito), HttpStatus.OK);
    }

    @GetMapping("/buscar/{cuenta}")
    public ResponseEntity <Cuenta> buscarPorNumeroCuenta (@PathVariable("cuenta") String numeroCuenta) {
        return new ResponseEntity<>(cuentaService.obtenerCuentaPorNumeroCuenta(numeroCuenta), HttpStatus.OK);
    }
    

}
