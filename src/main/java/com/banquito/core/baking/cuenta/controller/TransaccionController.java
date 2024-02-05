package com.banquito.core.baking.cuenta.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.baking.cuenta.domain.Transaccion;
import com.banquito.core.baking.cuenta.service.CreacionException;
import com.banquito.core.baking.cuenta.service.TransaccionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Transaccion> GetById(@PathVariable("id") Integer id) {
        try {
            log.info("Obteniendo transacción por ID: {}", id);
            return transaccionService.getById(id)
                    .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error al obtener transacción por ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Transaccion> Save(@RequestBody Transaccion transaccion) {
        try {
            log.info("Guardando transacción: {}", transaccion);
            return new ResponseEntity<>(transaccionService.create(transaccion), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al guardar transacción: {}", transaccion, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/depositar-monto")
    public ResponseEntity<Transaccion> depositarMonto(@RequestBody Map<String, Object> requestBody) {
        try {
            String numeroCuenta = (String) requestBody.get("numeroCuenta");
            BigDecimal valorDebe = new BigDecimal(requestBody.get("valorDebe").toString());
            Timestamp fechaCreacion = Timestamp.valueOf(requestBody.get("fechaCreacion").toString());
            log.info("Realizando depósito - Número de cuenta: {}, Monto: {}, Fecha: {}", numeroCuenta, valorDebe, fechaCreacion);
            return new ResponseEntity<>(transaccionService.depositar(numeroCuenta, valorDebe, fechaCreacion),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al realizar depósito: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/obtener-transacciones/{codCuenta}")
    public ResponseEntity<List<Transaccion>> obtenerTransacionesCliente(@PathVariable("codCuenta") Integer codCuenta) {
        try {
            log.info("Obteniendo transacciones para la cuenta con código: {}", codCuenta);
            List<Transaccion> transacciones = transaccionService.BuscarPorCodigoCuenta(codCuenta);
            return new ResponseEntity<>(transacciones, HttpStatus.OK);
        } catch (CreacionException e) {
            log.error("Error al obtener transacciones para la cuenta con código: {}", codCuenta, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Transaccion> Transferencia(@RequestBody Transaccion transaccion) {
        try {
            log.info("Realizando transferencia: {}", transaccion);
            return new ResponseEntity<>(transaccionService.transferencia(transaccion), HttpStatus.OK);
        } catch (CreacionException e) {
            log.error("Error al realizar transferencia: {}", transaccion, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/retirar")
    public ResponseEntity<Transaccion> retirar(@RequestBody Map<String, Object> requestBody) {
        try {
            String numeroCuenta = (String) requestBody.get("numeroCuenta");
            BigDecimal valorHaber = new BigDecimal(requestBody.get("valorHaber").toString());
            Timestamp fechaCreacion = Timestamp.valueOf(requestBody.get("fechaCreacion").toString());
            log.info("Realizando retiro - Número de cuenta: {}, Monto: {}, Fecha: {}", numeroCuenta, valorHaber, fechaCreacion);
            return new ResponseEntity<>(transaccionService.retirar(numeroCuenta, valorHaber, fechaCreacion),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al realizar retiro: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
