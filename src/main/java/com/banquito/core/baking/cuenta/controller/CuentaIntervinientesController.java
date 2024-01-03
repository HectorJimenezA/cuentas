package com.banquito.core.baking.cuenta.controller;

import java.util.List;

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

import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.CuentaIntervinientes;
import com.banquito.core.baking.cuenta.service.CuentaIntervinientesService;

@CrossOrigin
@RestController
@RequestMapping("/cuentaintervinientes")
public class CuentaIntervinientesController {
    private final CuentaIntervinientesService cuentaIntervinientesService;

    public CuentaIntervinientesController(CuentaIntervinientesService cuentaIntervinientesService) {
        this.cuentaIntervinientesService = cuentaIntervinientesService;
    }

    @GetMapping("/getbyid/{cuentaid}/{clientepersonaid}")
    public ResponseEntity<CuentaIntervinientes> GetById(@PathVariable("cuentaid") Integer cuentaId,
            @PathVariable("clientepersonaid") Integer clientePersonaId) {
        return cuentaIntervinientesService.getById(cuentaId, clientePersonaId)
                .map(register -> new ResponseEntity<>(register, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getbycuenta/{cuentaid}")
    public ResponseEntity<Iterable<CuentaIntervinientes>> GetByCuenta(@PathVariable("cuentaid") Integer cuentaId) {
        return new ResponseEntity<>(cuentaIntervinientesService.getByCuenta(cuentaId), HttpStatus.OK);
    }
    @GetMapping("/getcuentbyinter/{clientepersonaid}")
    public ResponseEntity<List<Cuenta>> GetByCuentaByInter(@PathVariable("clientepersonaid") Integer clientepersonaid) {
        return new ResponseEntity<>(cuentaIntervinientesService.getCuentaByInter(clientepersonaid), HttpStatus.OK);
    }

    @GetMapping("/getbycliente/{clientepersonaid}")
    public ResponseEntity<Iterable<CuentaIntervinientes>> GetByCodCliente(@PathVariable("clientepersonaid") Integer clientePersonaId) {
        return new ResponseEntity<>(cuentaIntervinientesService.getByCodCliente(clientePersonaId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<CuentaIntervinientes> Save(@RequestBody CuentaIntervinientes cuentaIntervinientes) {
        return new ResponseEntity<>(cuentaIntervinientesService.create(cuentaIntervinientes), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cuentaid}/{clientepersonaid}")
    public ResponseEntity<Boolean> Delete(@PathVariable("creditoid") Integer cuentaId,
            @PathVariable("clienteid") Integer clieclientePersonaIdnteId) {
        cuentaIntervinientesService.delete(cuentaId, clieclientePersonaIdnteId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CuentaIntervinientes> Update(@RequestBody CuentaIntervinientes cuentaIntervinientes) {
        return new ResponseEntity<>(cuentaIntervinientesService.update(cuentaIntervinientes), HttpStatus.OK);
    }
}
