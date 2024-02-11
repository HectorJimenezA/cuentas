package com.banquito.core.baking.cuenta.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.CuentaRepository;
import com.banquito.core.baking.cuenta.dao.TipoCuentaRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.TipoCuenta;

import jakarta.transaction.Transactional;

@Service

public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final TipoCuentaRepository tipoCuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository, TipoCuentaRepository tipoCuentaRepository) {
        this.cuentaRepository = cuentaRepository;
        this.tipoCuentaRepository = tipoCuentaRepository;
    }

    public Optional<Cuenta> getById(Integer codCuenta) {

        return this.cuentaRepository.findById(codCuenta);
    }

    public Iterable<TipoCuenta> listAll() {
        return this.tipoCuentaRepository.findAll();

    }

    @Transactional
    public TipoCuenta crearTipoCuenta(TipoCuenta tipoCuenta) {
        try {
            return this.tipoCuentaRepository.save(tipoCuenta);
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al crear el tipo de cuenta: " + tipoCuenta + " Error: " + e,
                    e);

        }
    }

    @Transactional
    public Cuenta create(Cuenta cuenta) {
        try {

            return this.cuentaRepository.save(cuenta);

        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException("Error en creacion de la cuenta: " + cuenta + ", Error: " + e, e);
        }
    }

    @Transactional
    public Cuenta update(Cuenta cuentaUpdate) {
        try {
            Optional<Cuenta> cuenta = getById(cuentaUpdate.getCodCuenta());
            if (cuenta.isPresent()) {
                return create(cuentaUpdate);
            } else {
                throw new RuntimeException(
                        "La cuenta con id" + cuentaUpdate.getCodCuenta() + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al actualizar la cuenta, error: " + e.getMessage(), e);
        }
    }

    public void delete(Integer id) {
        try {
            Optional<Cuenta> cuenta = getById(id);
            if (cuenta.isPresent()) {
                this.cuentaRepository.delete(cuenta.get());
            } else {
                throw new RuntimeException("La cuenta con el id" + id + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al eliminar la cuenta, error: " + e.getMessage(), e);
        }
    }

    public Cuenta obtenerCuentaPorNumeroCuenta(String numeroCuenta) {

        return this.cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    @Transactional
    public Cuenta actualizarBalance(Cuenta cuentaUpdate) {

        try {
            Optional<Cuenta> cuenta = this.cuentaRepository.findById(cuentaUpdate.getCodCuenta());
            if (cuenta.isPresent()) {
                cuentaUpdate
                        .setSaldoDisponible(cuenta.get().getSaldoDisponible().add(cuentaUpdate.getSaldoDisponible()));

                cuentaUpdate
                        .setSaldoContable(cuenta.get().getSaldoContable().add(cuentaUpdate.getSaldoContable()));
                return this.cuentaRepository.save(cuentaUpdate);
            } else {
                throw new RuntimeException("La cuenta con el id" + cuentaUpdate.getCodCuenta() + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al actualizar balance de la cuenta, error: " + e.getMessage(), e);
        }

    }


    public List<Cuenta> ObtenerCuentasCliente(Integer codCliente){
        try{
            List<Cuenta> cuentas = this.cuentaRepository.findByCodCliente(codCliente);
            if (!cuentas.isEmpty()) {
                return cuentas;
            } else {
                throw new RuntimeException("El cliente no tiene cuentas asociadas.");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al obtener cuentas del cliente " + e.getMessage(), e);
        }
    }

}
