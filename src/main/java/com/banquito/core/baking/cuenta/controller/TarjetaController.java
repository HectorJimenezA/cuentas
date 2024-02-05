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
import com.banquito.core.baking.cuenta.domain.Tarjeta;
import com.banquito.core.baking.cuenta.service.TarjetaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Tarjeta> GetById(@PathVariable("id") Integer id) {
        try {
            log.info("Obteniendo tarjeta por ID: {}", id);
            return tarjetaService.obtenerPorId(id)
                    .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error al obtener tarjeta por ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Tarjeta> Save(@RequestBody Tarjeta tarjeta) {
        try {
            log.info("Guardando tarjeta: {}", tarjeta);
            return new ResponseEntity<>(tarjetaService.crear(tarjeta), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al guardar tarjeta: {}", tarjeta, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> Delete(@PathVariable Integer id) {
        try {
            log.info("Eliminando tarjeta con ID: {}", id);
            tarjetaService.eliminar(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al eliminar tarjeta con ID: {}", id, e);
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Tarjeta> Update(@RequestBody Tarjeta tarjeta) {
        try {
            log.info("Actualizando tarjeta: {}", tarjeta);
            return new ResponseEntity<>(tarjetaService.actualizar(tarjeta), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al actualizar tarjeta: {}", tarjeta, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
