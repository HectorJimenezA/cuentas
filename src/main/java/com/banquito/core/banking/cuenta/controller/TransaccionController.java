package com.banquito.core.banking.cuenta.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.banking.cuenta.domain.Transaccion;
import com.banquito.core.banking.cuenta.service.TransaccionService;

@RestController
@RequestMapping("/transaccion")

public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("/getall")
    public List<Transaccion> GetAll() {

        return transaccionService.GetAll();
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Transaccion> GetById(@PathVariable("id") Integer id) {
        return transaccionService.GetById(id)
                .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findbyfechacreacion/{fechacreacion}")
    public ResponseEntity<List<Transaccion>> buscarPorFechaCreacion(
            @PathVariable("fechacreacion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Timestamp fechaCreacion) {
        List<Transaccion> transacciones = transaccionService.findByFechaCreacion(fechaCreacion);

        if (!transacciones.isEmpty()) {
            return new ResponseEntity<>(transacciones, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
