package com.banquito.core.baking.cuenta.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.TransaccionRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.Transaccion;

import jakarta.transaction.Transactional;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    } 

    public Optional<Transaccion> getById(Integer codTransaccion){
        return this.transaccionRepository.findById(codTransaccion);  
    }

   @Transactional
    public Transaccion create (Transaccion transaccion){
        try {
                return this.transaccionRepository.save(transaccion);
        
        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException("Error en creacion de la transaccion: "+transaccion+", Error: "+e, e);
        }
    }


}
