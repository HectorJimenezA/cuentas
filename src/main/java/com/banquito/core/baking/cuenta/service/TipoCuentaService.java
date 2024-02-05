package com.banquito.core.baking.cuenta.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
public class TipoCuentaService {
    private final TipoCuentaRepository tipoCuentaRepository;

    public TipoCuentaService(TipoCuentaRepository tipoCuentaRepository) {
        this.tipoCuentaRepository = tipoCuentaRepository;
    }

    public TipoCuentaDTO obtenerPorId(String codTipoCuenta) {

        log.info("Obtener la cuenta");
        TipoCuenta tipoCuenta = this.tipoCuentaRepository.findById(codTipoCuenta).get();
        log.info("Se ha obtenido la cuenta {}",tipoCuenta);
        return TipoCuentaBuilder.toDTO(tipoCuenta);
    }
   
    public List<TipoCuentaDTO> listarTodos() {

        List<TipoCuentaDTO> dtos = new ArrayList<>();
        for (TipoCuenta tipoCuenta : this.tipoCuentaRepository.findAll()){
            dtos.add(TipoCuentaBuilder.toDTO(tipoCuenta));
        }
        return dtos;

    }

    @Transactional
    public TipoCuenta crearTipoCuenta(TipoCuentaDTO dto) {
        try {
            TipoCuenta tipoCuenta=TipoCuentaBuilder.toTipoCuenta(dto);
            tipoCuenta.setFechaCreacion(Timestamp.from(Instant.now()));
            return this.tipoCuentaRepository.save(tipoCuenta);
        } catch (Exception e) {
            log.error("Error al crear el tipo de cuenta: {}", dto);
            throw new CreacionException("Ocurrio un error al crear el tipo de cuenta: " + dto + " Error: " + e,e);

        }
    }

    public TipoCuenta update(TipoCuenta tipoCuentaUpdate) {
        try {
            Optional<TipoCuenta> tipoCuenta = obtenerPorId(tipoCuentaUpdate.getCodTipoCuenta());
            if (tipoCuenta.isPresent()) {
                return crearTipoCuenta(tipoCuentaUpdate);
            } else {
                throw new RuntimeException(
                        "El tipo de cuenta con id" + tipoCuentaUpdate.getCodTipoCuenta() + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al actualizar el Tipo de cuenta, error: " + e.getMessage(),
                    e);
        }
    }

    public void delete(String id) {
        try {
            Optional<TipoCuenta> tipoCuenta = obtenerPorId(id);
            if (tipoCuenta.isPresent()) {
                this.tipoCuentaRepository.delete(tipoCuenta.get());
            } else {
                throw new RuntimeException("El tipo de cuenta con el id" + id + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al eliminar el tipo de cuenta, error: " + e.getMessage(), e);

        }
    }
}