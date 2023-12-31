package com.banquito.core.baking.cuenta.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.banquito.core.baking.cuenta.dao.TransaccionRepository;
import com.banquito.core.baking.cuenta.domain.Transaccion;

import jakarta.transaction.Transactional;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;



    public Optional<Transaccion> getById(Integer codTransaccion) {
        return this.transaccionRepository.findById(codTransaccion);
    }

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Transactional
    public Transaccion create(Transaccion transaccion) {
        try {
            return this.transaccionRepository.save(transaccion);

        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException("Error en creacion de la transaccion: " + transaccion + ", Error: " + e, e);
        }
    }

    @Transactional
    public Transaccion depositar(Transaccion transaccionBeneficiario) {
        try {
            
            if ("DEP".equals(transaccionBeneficiario.getTipoTransaccion())) {
                System.out.println("Holaaaaaaaaaaaa"+transaccionBeneficiario.toString());
                transaccionBeneficiario.hashCode();
                return this.transaccionRepository.save(transaccionBeneficiario);
            } else {
               
                throw new RuntimeException("El tipo de cuenta no es compatible con depósitos");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException(
                    "Error en creacion de la transaccion: " + transaccionBeneficiario + ", Error: " + e, e);
        }

    }

    public List <Transaccion> BuscarPorCodigoCuenta(Integer codCuentaOrigen) {
        return this.transaccionRepository.findByCodCuentaOrigen(codCuentaOrigen);
    }

}
