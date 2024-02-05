package com.banquito.core.baking.cuenta.service;

import java.util.Optional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.CuentaRepository;
import com.banquito.core.baking.cuenta.dao.TipoCuentaRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.TipoCuenta;
import com.banquito.core.baking.cuenta.dto.CuentaBuilder;
import com.banquito.core.baking.cuenta.dto.CuentaDTO;
import com.banquito.core.baking.cuenta.dto.TipoCuentaBuilder;
import com.banquito.core.baking.cuenta.dto.TipoCuentaDTO;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final TipoCuentaRepository tipoCuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository, TipoCuentaRepository tipoCuentaRepository) {
        this.cuentaRepository = cuentaRepository;
        this.tipoCuentaRepository = tipoCuentaRepository;
    }

    public CuentaDTO obtenerPorId(Integer codCuenta) {

        log.info("Obtener la cuenta");
        Cuenta cuenta = this.cuentaRepository.findById(codCuenta).get();
        log.info("Se ha obtenido la cuenta {}",cuenta);
        return CuentaBuilder.toDTO(cuenta);
    }

    

    @Transactional
    public Cuenta crear(CuentaDTO dto) {
        try {

            Cuenta cuenta=CuentaBuilder.toCuenta(dto);
            cuenta.setFechaCreacion(Timestamp.from(Instant.now()));
            cuenta.setEstado("ACT");
            return this.cuentaRepository.save(cuenta);

        } catch (Exception e) {
            log.error("Error al crear la  cuenta: {}", dto);
            throw new CreacionException("Error en creacion de la cuenta: " + dto + ", Error: " + e, e);
        }
    }

    @Transactional
    public Cuenta actualizar(Cuenta cuentaUpdate) {
        try {
            Optional<Cuenta> cuenta = obtenerPorId(cuentaUpdate.getCodCuenta());
            if (cuenta.isPresent()) {
                return crear(cuentaUpdate);
            } else {
                throw new RuntimeException(
                        "La cuenta con id" + cuentaUpdate.getCodCuenta() + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al actualizar la cuenta, error: " + e.getMessage(), e);
        }
    }

    public void eliminar(Integer id) {
        try {
            Optional<Cuenta> cuenta = obtenerPorId(id);
            if (cuenta.isPresent()) {
                this.cuentaRepository.delete(cuenta.get());
            } else {
                throw new RuntimeException("La cuenta con el id" + id + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al eliminar la cuenta, error: " + e.getMessage(), e);
        }
    }

    public CuentaDTO obtenerCuentaPorNumeroCuenta(String numeroCuenta) {

        log.info("Obtener la cuenta");
        Cuenta cuenta = this.cuentaRepository.findByNumeroCuenta(numeroCuenta);
        log.info("Se ha obtenido la cuenta {}",cuenta);
        return CuentaBuilder.toDTO(cuenta);
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


    public List<CuentaDTO> ObtenerCuentasCliente(Integer codCliente){

        try{
        List<CuentaDTO> dtos = new ArrayList<>();
        List<Cuenta> cuentas = this.cuentaRepository.findByCodCliente(codCliente);
            if (!cuentas.isEmpty()) {
                for (Cuenta cuenta : cuentas ){
                    dtos.add(CuentaBuilder.toDTO(cuenta));
                }
                return dtos;
            } else {
                throw new RuntimeException("El cliente no tiene cuentas asociadas.");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al obtener cuentas del cliente " + e.getMessage(), e);
        }
    }
}
