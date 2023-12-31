package com.banquito.core.baking.cuenta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.service.CreacionException;
import com.banquito.core.baking.cuenta.service.CuentaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    private CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

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
    public ResponseEntity<Cuenta> buscarPorNumeroCuenta(@PathVariable("cuenta") String numeroCuenta) {
        return new ResponseEntity<>(cuentaService.obtenerCuentaPorNumeroCuenta(numeroCuenta), HttpStatus.OK);
    }

    @PutMapping("/actualizar/balance")
    public ResponseEntity<Cuenta> UpdateBalance(@RequestBody Cuenta cuenta) {
        return new ResponseEntity<>(cuentaService.actualizarBalance(cuenta), HttpStatus.OK);
    }

    @GetMapping("/obtenerCuentasCliente/{codCliente}")
    public ResponseEntity<List<Cuenta>> obtenerCuentasCliente(@PathVariable("codCliente") Integer codCliente) {
        try {
            List<Cuenta> cuentas = cuentaService.ObtenerCuentasCliente(codCliente);
            return new ResponseEntity<>(cuentas, HttpStatus.OK);
        } catch (CreacionException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
