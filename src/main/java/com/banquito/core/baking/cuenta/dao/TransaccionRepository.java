package com.banquito.core.baking.cuenta.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.baking.cuenta.domain.Transaccion;


@Repository
public interface TransaccionRepository extends CrudRepository<Transaccion, Integer>{
    // List<Transaccion> findByCodCuentaAndFechaCreacion(Integer codCuenta, Timestamp fechaCreacion); 
    // List<Transaccion> findByCodCuentaAndFechaCreacionAndTipoTransaccion(Integer codCuenta, Timestamp fechaCreacion, String tipoTransaccion); 
    // List<Transaccion> findByCodCuentaAndFechaCreacionAndEstado(Integer codCuenta, Timestamp fechaCreacion, String estado); 
    // List<Transaccion> findByCodCuentaAndTipoTransaccionAndEstado(Integer codCuenta, String tipoTransaccion, String estado); 
   


}
