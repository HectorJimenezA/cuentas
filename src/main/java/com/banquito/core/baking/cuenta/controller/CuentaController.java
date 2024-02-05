package com.banquito.core.baking.cuenta.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.banquito.core.baking.cuenta.service.CreacionException;
import com.banquito.core.baking.cuenta.service.CuentaService;

@RestController
@RequestMapping("/api/v1/cuentas")
public class CuentaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CuentaController.class);

    private CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> GetById(@PathVariable("id") Integer id) {
        try {
            LOGGER.info("Recuperando cuenta por id: {}", id);
            return cuentaService.getById(id)
                    .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            LOGGER.error("Error al recuperar la cuenta con ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Cuenta> Save(@RequestBody Cuenta cuenta) {
        try {
            LOGGER.info("Guardando cuenta: {}", cuenta);
            return new ResponseEntity<>(cuentaService.crear(cuenta), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al guardar la cuenta: {}", cuenta, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Boolean> Delete(@PathVariable Integer id) {
        try {
            LOGGER.info("Eliminando la cuenta con el ID: {}", id);
            cuentaService.eliminar(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al eliminar la ceunta con el ID: {}", id, e);
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Cuenta> Update(@RequestBody Cuenta credito) {
        try {
            LOGGER.info("Actualizando cuenta: {}", credito);
            return new ResponseEntity<>(cuentaService.actualizar(credito), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al actualizar la cuenta: {}", credito, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{cuenta}")
    public ResponseEntity<Cuenta> buscarPorNumeroCuenta(@PathVariable("cuenta") String numeroCuenta) {
        try {
            LOGGER.info("Buscando numero de cuenta: {}", numeroCuenta);
            return new ResponseEntity<>(cuentaService.obtenerCuentaPorNumeroCuenta(numeroCuenta), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al buscar el numero de la cuenta: {}", numeroCuenta, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Cuenta> UpdateBalance(@RequestBody Cuenta cuenta) {
        try {
            LOGGER.info("Actualización del saldo de la cuenta: {}", cuenta);
            return new ResponseEntity<>(cuentaService.actualizarBalance(cuenta), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al actualizar el saldo de la cuenta: {}", cuenta, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{codCliente}")
    public ResponseEntity<List<Cuenta>> obtenerCuentasCliente(@PathVariable("codCliente") Integer codCliente) {
        try {
            LOGGER.info("Obteniendo cuentas para cliente con código: {}", codCliente);
            List<Cuenta> cuentas = cuentaService.ObtenerCuentasCliente(codCliente);
            return new ResponseEntity<>(cuentas, HttpStatus.OK);
        } catch (CreacionException e) {
            LOGGER.error("Error al obtener cuentas para cliente con código: {}", codCliente, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
