package com.banquito.core.baking.cuenta.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.TipoCuentaRepository;
import com.banquito.core.baking.cuenta.domain.TipoCuenta;

import jakarta.transaction.Transactional;

@Service
public class TipoCuentaService {
    private final TipoCuentaRepository tipoCuentaRepository;

    public TipoCuentaService(TipoCuentaRepository tipoCuentaRepository) {
        this.tipoCuentaRepository = tipoCuentaRepository;
    }

    public Optional<TipoCuenta> getById(String codTipoCuenta) {
        
        return this.tipoCuentaRepository.findById(codTipoCuenta);

    }
    public Iterable<TipoCuenta> listAll() {
        return this.tipoCuentaRepository.findAll();
    }

    @Transactional
    public TipoCuenta create(TipoCuenta tipoCuenta) {
        try {

            return this.tipoCuentaRepository.save(tipoCuenta);

        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException("Error en creacion del Tipo de Cuenta: " + tipoCuenta + ", Error: " + e, e);
        }
    }

    public TipoCuenta update(TipoCuenta tipoCuentaUpdate) {
        try {
            Optional<TipoCuenta> tipoCuenta = getById(tipoCuentaUpdate.getCodTipoCuenta());
            if (tipoCuenta.isPresent()) {
                return create(tipoCuentaUpdate);
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
            Optional<TipoCuenta> tipoCuenta = getById(id);
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