package com.banquito.core.baking.cuenta.controller;

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

import com.banquito.core.baking.cuenta.domain.CuentaIntervinientes;
import com.banquito.core.baking.cuenta.service.CuentaIntervinientesService;

@RestController
@RequestMapping("/api/v1//cuentaintervinientes")
public class CuentaIntervinientesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuentaIntervinientesController.class);

    private final CuentaIntervinientesService cuentaIntervinientesService;

    public CuentaIntervinientesController(CuentaIntervinientesService cuentaIntervinientesService) {
        this.cuentaIntervinientesService = cuentaIntervinientesService;
    }

    @GetMapping("/getbyid/{cuentaid}/{clientepersonaid}")
    public ResponseEntity<CuentaIntervinientes> GetById(@PathVariable("cuentaid") Integer cuentaId,
            @PathVariable("clientepersonaid") Integer clientePersonaId) {
        try {
            LOGGER.info("Obteniendo cuenta de intervinientes por ID: {}, ClientePersonaID: {}", cuentaId, clientePersonaId);
            return cuentaIntervinientesService.getById(cuentaId, clientePersonaId)
                    .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            LOGGER.error("Error al obtener cuenta de intervinientes por ID: {}, ClientePersonaID: {}", cuentaId, clientePersonaId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<CuentaIntervinientes> Save(@RequestBody CuentaIntervinientes cuentaIntervinientes) {
        try {
            LOGGER.info("Guardando cuenta de intervinientes: {}", cuentaIntervinientes);
            return new ResponseEntity<>(cuentaIntervinientesService.create(cuentaIntervinientes), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al guardar cuenta de intervinientes: {}", cuentaIntervinientes, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{cuentaid}/{clientepersonaid}")
    public ResponseEntity<Boolean> Delete(@PathVariable("cuentaid") Integer cuentaId,
            @PathVariable("clientepersonaid") Integer clientePersonaId) {
        try {
            LOGGER.info("Eliminando cuenta de intervinientes con ID: {}, ClientePersonaID: {}", cuentaId, clientePersonaId);
            cuentaIntervinientesService.delete(cuentaId, clientePersonaId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al eliminar cuenta de intervinientes con ID: {}, ClientePersonaID: {}", cuentaId, clientePersonaId, e);
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CuentaIntervinientes> Update(@RequestBody CuentaIntervinientes cuentaIntervinientes) {
        try {
            LOGGER.info("Actualizando cuenta de intervinientes: {}", cuentaIntervinientes);
            return new ResponseEntity<>(cuentaIntervinientesService.update(cuentaIntervinientes), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error al actualizar cuenta de intervinientes: {}", cuentaIntervinientes, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
