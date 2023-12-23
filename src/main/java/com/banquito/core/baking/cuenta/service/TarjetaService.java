package com.banquito.core.baking.cuenta.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.TarjetaRepository;
import com.banquito.core.baking.cuenta.domain.Tarjeta;

import jakarta.transaction.Transactional;

@Service
public class TarjetaService {
    private final TarjetaRepository tarjetaRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    public Optional<Tarjeta> getById(Integer codTarjeta) {

        return this.tarjetaRepository.findById(codTarjeta);

    }

    @Transactional
    public Tarjeta create(Tarjeta tarjeta) {
        try {

            return this.tarjetaRepository.save(tarjeta);

        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException("Error en creacion de la tarjeta: " + tarjeta + ", Error: " + e, e);
        }
    }

    public Tarjeta update(Tarjeta tarjetaUpdate) {
        try {
            Optional<Tarjeta> tarjeta = getById(tarjetaUpdate.getCodTarjeta());
            if (tarjeta.isPresent()) {
                return create(tarjetaUpdate);
            } else {
                throw new RuntimeException(
                        "La tarjeta con el id" + tarjetaUpdate.getCodTarjeta() + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al actualizar la tarjeta, error: " + e.getMessage(), e);
        }
    }

    public void delete(Integer codTarjeta) {
        try {
            Optional<Tarjeta> tarjeta = getById(codTarjeta);
            if (tarjeta.isPresent()) {
                this.tarjetaRepository.delete(tarjeta.get());
            } else {
                throw new RuntimeException("La tarjeta con el id" + codTarjeta + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al eliminar la tarjeta, error: " + e.getMessage(), e);
        }
    }

}
