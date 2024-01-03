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


@CrossOrigin
@RestController
@RequestMapping("/transaccion")
public class TransaccionController {
    private TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

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

    // @PostMapping("/depositar")
    // public ResponseEntity<Transaccion> Depositar(@RequestBody Transaccion transaccion) {
    //     return new ResponseEntity<>(transaccionService.depositar(transaccion), HttpStatus.OK);
    // }
    
    @PostMapping("/depositar-monto")
    public ResponseEntity<Transaccion> depositarMonto(@RequestBody Map<String, Object> requestBody) {
        String numeroCuenta = (String) requestBody.get("numeroCuenta");
        BigDecimal valorDebe = new BigDecimal(requestBody.get("valorDebe").toString());
        Timestamp fechaCreacion = Timestamp.valueOf(requestBody.get("fechaCreacion").toString());

        return new ResponseEntity<>(transaccionService.depositar(numeroCuenta, valorDebe, fechaCreacion),
                HttpStatus.OK);
    }

    @PostMapping("/retirar")
    public ResponseEntity<Transaccion> retirar (@RequestBody Map<String, Object> requestBody) {
        String numeroCuenta = (String) requestBody.get("numeroCuenta");
        BigDecimal valorHaber = new BigDecimal(requestBody.get("valorHaber").toString());
        Timestamp fechaCreacion = Timestamp.valueOf(requestBody.get("fechaCreacion").toString());

        return new ResponseEntity<>(transaccionService.retirar(numeroCuenta, valorHaber, fechaCreacion),
                HttpStatus.OK);
    }


    @GetMapping("/obtener-transacciones/{codCuenta}")
    public ResponseEntity<List<Transaccion>> obtenerTransacionesCliente(@PathVariable("codCuenta") Integer codCuenta) {
        try {
            List<Transaccion> cuentas = transaccionService.BuscarPorCodigoCuenta(codCuenta);
            return new ResponseEntity<>(cuentas, HttpStatus.OK);
        } catch (CreacionException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Transaccion> Transferencia(@RequestBody Transaccion transaccion) {
        try {
            return new ResponseEntity<>(transaccionService.transferencia(transaccion), HttpStatus.OK);
        } catch (CreacionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
