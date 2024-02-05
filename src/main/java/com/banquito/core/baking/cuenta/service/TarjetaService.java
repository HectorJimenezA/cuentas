package com.banquito.core.baking.cuenta.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.TarjetaRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.Tarjeta;
import com.banquito.core.baking.cuenta.dto.CuentaBuilder;
import com.banquito.core.baking.cuenta.dto.CuentaDTO;
import com.banquito.core.baking.cuenta.dto.TarjetaBuilder;
import com.banquito.core.baking.cuenta.dto.TarjetaDTO;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class TarjetaService {
    private final TarjetaRepository tarjetaRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    public TarjetaDTO obtenerPorId(Integer codTarjeta) {

        log.info("Obtener la cuenta");
        Tarjeta tarjeta = this.tarjetaRepository.findById(codTarjeta).get();
        log.info("Se ha obtenido la tarjeta {}",tarjeta);
        
        return TarjetaBuilder.toDTO(tarjeta);
    }

    @Transactional
    public Tarjeta crear(TarjetaDTO dto) {
        try {

            Tarjeta tarjeta=TarjetaBuilder.toTarjeta(dto);
            tarjeta.setFechaEmision(Timestamp.from(Instant.now()));
            tarjeta.setEstado("ACT");
            return this.tarjetaRepository.save(tarjeta);

        } catch (Exception e) {
            log.error("Error al crear la  tarjeta: {}", dto);
            throw new CreacionException("Error en creacion de la tarjeta: " + dto + ", Error: " + e, e);
        }
    }




    public Tarjeta actualizar(Tarjeta tarjetaUpdate) {
        try {
            Optional<Tarjeta> tarjeta = obtenerPorId(tarjetaUpdate.getCodTarjeta());
            if (tarjeta.isPresent()) {
                return crear(tarjetaUpdate);
            } else {
                throw new RuntimeException(
                        "La tarjeta con el id" + tarjetaUpdate.getCodTarjeta() + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al actualizar la tarjeta, error: " + e.getMessage(), e);
        }
    }

    public void eliminar(Integer codTarjeta) {
        try {
            Optional<Tarjeta> tarjeta = obtenerPorId(codTarjeta);
            if (tarjeta.isPresent()) {
                this.tarjetaRepository.delete(tarjeta.get());
            } else {
                throw new RuntimeException("La tarjeta con el id" + codTarjeta + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al eliminar la tarjeta, error: " + e.getMessage(), e);
        }
    }

    public Tarjeta buscarPorTarjeta(String numero) {
        return this.tarjetaRepository.findByNumero(numero);

    }
}
