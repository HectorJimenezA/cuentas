package com.banquito.core.baking.cuenta.controller;

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

import com.banquito.core.baking.cuenta.domain.TipoCuenta;
import com.banquito.core.baking.cuenta.service.TipoCuentaService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/tipoCuentas")
public class TipoCuentaController {

    private final TipoCuentaService tipoCuentaService;

    public TipoCuentaController(TipoCuentaService tipoCuentaService) {
        this.tipoCuentaService = tipoCuentaService;
    }

    @GetMapping("{id}")
    public ResponseEntity<TipoCuenta> GetById(@PathVariable("id") String id) {
        try {
            log.info("Obteniendo tipo de cuenta por ID: {}", id);
            return tipoCuentaService.obtenerPorId(id)
                    .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error al obtener tipo de cuenta por ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<TipoCuenta> Save(@RequestBody TipoCuenta tipoCuenta) {
        try {
            log.info("Guardando tipo de cuenta: {}", tipoCuenta);
            return new ResponseEntity<>(tipoCuentaService.create(tipoCuenta), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al guardar tipo de cuenta: {}", tipoCuenta, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> Delete(@PathVariable("id") String id) {
        try {
            log.info("Eliminando tipo de cuenta con ID: {}", id);
            tipoCuentaService.delete(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al eliminar tipo de cuenta con ID: {}", id, e);
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoCuenta> Update(@RequestBody TipoCuenta tipoCuenta) {
        try {
            log.info("Actualizando tipo de cuenta: {}", tipoCuenta);
            return new ResponseEntity<>(tipoCuentaService.update(tipoCuenta), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al actualizar tipo de cuenta: {}", tipoCuenta, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
