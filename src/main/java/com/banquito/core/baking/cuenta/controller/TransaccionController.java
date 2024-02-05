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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.baking.cuenta.domain.Transaccion;
import com.banquito.core.baking.cuenta.dto.TransaccionDTO;
import com.banquito.core.baking.cuenta.service.CreacionException;
import com.banquito.core.baking.cuenta.service.TransaccionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/transacciones")
public class TransaccionController {
    private TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    // @GetMapping("/getall")
    // public ResponseEntity<Iterable<Transaccion>> GetAll() {
    // return new ResponseEntity<>(TransaccionService.listAll(), HttpStatus.OK);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> ObtenerPorId(@PathVariable("id") Integer id) {
        log.info("Recibida solicitud para obtener la transacción con ID: {}", id);
        try {
            return ResponseEntity.ok(this.transaccionService.getById(id));
        } catch(RuntimeException rte) {
            log.error("Error al obtener la transacción: {}", rte);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<Void> Guardar(@RequestBody TransaccionDTO transaccion) {
        log.info("Transacción guardada con éxito: {}", transaccion);
        try {
            this.transaccionService.crear(transaccion);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException rte) {
            log.error("Error al generar la transaccion: {}", rte);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/depositar-monto")
    public ResponseEntity<Transaccion> depositarMonto(@RequestBody Map<String, Object> requestBody) {
        log.info("Recibida solicitud para depositar monto en la cuenta. Detalles: {}", requestBody);
        String numeroCuenta = (String) requestBody.get("numeroCuenta");
        BigDecimal valorDebe = new BigDecimal(requestBody.get("valorDebe").toString());
        Timestamp fechaCreacion = Timestamp.valueOf(requestBody.get("fechaCreacion").toString());

        log.info("Depósito realizado con éxito. Detalles de la transacción: {}", requestBody);

        return new ResponseEntity<>(transaccionService.depositar(numeroCuenta, valorDebe, fechaCreacion),
                HttpStatus.OK);
    }

    @GetMapping("/obtener-transacciones/{codCuenta}")
    public ResponseEntity<List<TransaccionDTO>> obtenerTransacionesCliente(@PathVariable("codCuenta") Integer codCuenta) {
        log.info("Recibida solicitud para obtener transacciones para la cuenta con código: {}", codCuenta);
        try {
            List<TransaccionDTO> transacciones = transaccionService.BuscarPorCodigoCuenta(codCuenta);
            return ResponseEntity.ok(transacciones);
        } catch (CreacionException e) {
            log.error("Error al obtener transacciones", e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/transferencia")
    public ResponseEntity<Transaccion> Transferencia(@RequestBody Transaccion transaccion) {
        log.info("Recibida solicitud para realizar una transferencia. Detalles de la transacción: {}", transaccion);
        try {
            Transaccion transferenciaRealizada = transaccionService.transferencia(transaccion);
    
            log.info("Transferencia realizada con éxito. Detalles de la transacción: {}", transferenciaRealizada);
    
            return new ResponseEntity<>(transferenciaRealizada, HttpStatus.OK);
        } catch (CreacionException e) {
            log.error("Error al realizar la transferencia. Detalles del error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/retirar")
    public ResponseEntity<Transaccion> retirar (@RequestBody Map<String, Object> requestBody) {
        log.info("Recibida solicitud para realizar un retiro. Detalles: {}", requestBody);

        String numeroCuenta = (String) requestBody.get("numeroCuenta");
        BigDecimal valorHaber = new BigDecimal(requestBody.get("valorHaber").toString());
        Timestamp fechaCreacion = Timestamp.valueOf(requestBody.get("fechaCreacion").toString());

        try {
            Transaccion retiroRealizado = transaccionService.retirar(numeroCuenta, valorHaber, fechaCreacion);
    
            log.info("Retiro realizado con éxito. Detalles de la transacción: {}", retiroRealizado);
    
            return new ResponseEntity<>(retiroRealizado, HttpStatus.OK);
        } catch (CreacionException e) {
            log.error("Error al realizar el retiro. Detalles del error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
