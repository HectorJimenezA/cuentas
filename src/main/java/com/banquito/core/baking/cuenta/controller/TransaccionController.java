package com.banquito.core.baking.cuenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.baking.cuenta.domain.Transaccion;
import com.banquito.core.baking.cuenta.service.TransaccionService;

@RestController
@RequestMapping("/transaccion")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    // @GetMapping("/getall")
    // public ResponseEntity<Iterable<Transaccion>> GetAll() {
    // return new ResponseEntity<>(TransaccionService.listAll(), HttpStatus.OK);
    // }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Transaccion> GetById(@PathVariable("id") Integer id) {
        return transaccionService.getById(id)
                .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Transaccion> Save(@RequestBody Transaccion transaccion) {
        return new ResponseEntity<>(transaccionService.create(transaccion), HttpStatus.OK);
    }

    @PostMapping("/depositar")
    public ResponseEntity<Transaccion> Depositar(@RequestBody Transaccion transaccion) {
        return new ResponseEntity<>(transaccionService.depositar(transaccion), HttpStatus.OK);
    }

}
