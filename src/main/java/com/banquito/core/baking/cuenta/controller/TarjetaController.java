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

import com.banquito.core.baking.cuenta.domain.Tarjeta;
import com.banquito.core.baking.cuenta.service.TarjetaService;

@CrossOrigin
@RestController
@RequestMapping("/tarjeta")
public class TarjetaController {
    private TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Tarjeta> GetById(@PathVariable("id") Integer id) {
        return tarjetaService.getById(id)
                .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Tarjeta> Save(@RequestBody Tarjeta tarjeta) {
        return new ResponseEntity<>(tarjetaService.create(tarjeta), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> Delete(@PathVariable Integer id) {
        tarjetaService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Tarjeta> Update(@RequestBody Tarjeta tarjeta) {
        return new ResponseEntity<>(tarjetaService.update(tarjeta), HttpStatus.OK);
    }

    @GetMapping("/buscar-tarjeta/{tarjeta}")
    public ResponseEntity<Tarjeta> buscarPorTarjeta(@PathVariable("tarjeta") String numero) {
        return new ResponseEntity<>(tarjetaService.buscarPorTarjeta(numero), HttpStatus.OK);
    }
}
