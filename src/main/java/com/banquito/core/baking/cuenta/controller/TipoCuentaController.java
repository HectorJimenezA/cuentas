package com.banquito.core.baking.cuenta.controller;

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

import com.banquito.core.baking.cuenta.domain.TipoCuenta;
import com.banquito.core.baking.cuenta.service.TipoCuentaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tipocuenta")
public class TipoCuentaController {
    private TipoCuentaService tipoCuentaService;

    public TipoCuentaController(TipoCuentaService tipoCuentaService) {
        this.tipoCuentaService = tipoCuentaService;
    }

    @GetMapping("/getall")
    public ResponseEntity<Iterable<TipoCuenta>> GetAll() {
        return new ResponseEntity<>(tipoCuentaService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<TipoCuenta> GetById(@PathVariable("id") String id) {
        return tipoCuentaService.getById(id)
                .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<TipoCuenta> Save(@RequestBody TipoCuenta tipoCuenta) {
        return new ResponseEntity<>(tipoCuentaService.create(tipoCuenta), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> Delete(@PathVariable("id") String id) {
        tipoCuentaService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<TipoCuenta> Update(@RequestBody TipoCuenta tipoCuenta) {
        return new ResponseEntity<>(tipoCuentaService.update(tipoCuenta), HttpStatus.OK);
    }
}
