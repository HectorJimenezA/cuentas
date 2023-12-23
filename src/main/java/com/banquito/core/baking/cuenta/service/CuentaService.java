package com.banquito.core.baking.cuenta.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.CuentaRepository;
import com.banquito.core.baking.cuenta.dao.TipoCuentaRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.TipoCuenta;

import jakarta.transaction.Transactional;

@Service

public class CuentaService {
    private final CuentaRepository CuentaRepository;
    private final TipoCuentaRepository tipoCuentaRepository;

    

    public CuentaService(CuentaRepository cuentaRepository,
            TipoCuentaRepository tipoCuentaRepository) {
        CuentaRepository = cuentaRepository;
        this.tipoCuentaRepository = tipoCuentaRepository;
    }

    public Optional <Cuenta> getById(Integer id){

            return  this.CuentaRepository.findById(id);
    }

    public Iterable <TipoCuenta> listAll(){
        return this.tipoCuentaRepository.findAll();

    }

    @Transactional
    public TipoCuenta crearTipoCuenta (TipoCuenta tipoCuenta)
   {
     try {
        return this.tipoCuentaRepository.save(tipoCuenta);
     } catch (Exception e) {
        throw new CreacionException("Ocurrio un error al crear el tipo de cuenta: "+tipoCuenta+" Error: "+e,e);

     }
   } 

   @Transactional
    public Cuenta create (Cuenta cuenta){
        try {
          
                return this.CuentaRepository.save(cuenta);
            
        
        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException("Error en creacion de la cuenta: "+cuenta+", Error: "+e, e);
        }
    }

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
            Optional<Cuenta> credito = getById(id);
            if (credito.isPresent()) {
                this.CuentaRepository.delete(credito.get());
            } else {
                throw new RuntimeException("La cuenta con el id" + id + " no existe");
            }
        } catch (Exception e) {
            throw new CreacionException("Ocurrio un error al eliminar la cuenta, error: " + e.getMessage(), e);
        }
    }

}
