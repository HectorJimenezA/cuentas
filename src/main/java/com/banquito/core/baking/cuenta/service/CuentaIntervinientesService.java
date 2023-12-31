package com.banquito.core.baking.cuenta.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.CuentaIntervinientesRepository;
import com.banquito.core.baking.cuenta.domain.CuentaIntervinientes;
import com.banquito.core.baking.cuenta.domain.CuentaIntervinientesPK;

import jakarta.transaction.Transactional;

@Service
public class CuentaIntervinientesService {
    private final CuentaIntervinientesRepository cuentaIntervinientesRepository;

    public CuentaIntervinientesService(CuentaIntervinientesRepository cuentaIntervinientesRepository) {
        this.cuentaIntervinientesRepository = cuentaIntervinientesRepository;
    }

    public Optional<CuentaIntervinientes> getById(Integer codCuenta, Integer codClientePersona) {
        CuentaIntervinientesPK cuentaIntervinientePK = new CuentaIntervinientesPK(codCuenta, codClientePersona);
        return this.cuentaIntervinientesRepository.findById(cuentaIntervinientePK);
    }

    public Iterable<CuentaIntervinientes> getByCuenta(Integer codCuenta) {
        return this.cuentaIntervinientesRepository.findByPKCodCuenta(codCuenta);
    }

    public Iterable<CuentaIntervinientes> getByCodCliente(Integer CodClientePersona) {
        return this.cuentaIntervinientesRepository.findByPKCodClientePersona(CodClientePersona);
    }

    @Transactional
    public CuentaIntervinientes create(CuentaIntervinientes cuentaIntervinientes) {
        try {

            return this.cuentaIntervinientesRepository.save(cuentaIntervinientes);

        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException(
                    "Error en creacion de los Intervinientes en la cuenta: " + cuentaIntervinientes + ", Error: " + e,
                    e);
        }
    }

    public CuentaIntervinientes update(CuentaIntervinientes cuentaIntervinientesUpdate) {
        try {
            Optional<CuentaIntervinientes> cuentaIntervinientes = getById(
                    cuentaIntervinientesUpdate.getPK().getCodCuenta(),
                    cuentaIntervinientesUpdate.getPK().getCodClientePersona());
            if (cuentaIntervinientes.isPresent()) {
                return create(cuentaIntervinientes.get());
            } else {
                throw new RuntimeException(
                        "La cuenta Intervinientes con id" + cuentaIntervinientesUpdate.getPK().getCodCuenta() + "-"
                                + cuentaIntervinientesUpdate.getPK().getCodClientePersona() + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al actualizar la cuenta, error: " + e.getMessage(), e);
        }
    }

    public void delete(Integer codCuenta, Integer codClientePersona) {
        try {
            Optional<CuentaIntervinientes> credito = getById(codCuenta, codClientePersona);
            if (credito.isPresent()) {
                this.cuentaIntervinientesRepository.delete(credito.get());
            } else {
                throw new RuntimeException(
                        "La cuenta Intervinientes con id" + codCuenta + "-" + codClientePersona + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException(
                    "Ocurrio un error al eliminar la cuenta intervinientes, error: " + e.getMessage(), e);
        }
    }

}
