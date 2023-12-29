package com.banquito.core.baking.cuenta.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.baking.cuenta.domain.Tarjeta;

@Repository
public interface TarjetaRepository extends CrudRepository<Tarjeta, Integer> {

    ///List<Tarjeta> findByCodCuentaAndFechaCreacion(Integer codCuenta, Timestamp fechaCreacion); 
    //List<Tarjeta> findByNumeroAndFechaCreacion(Integer codCuenta, Timestamp fechaCreacion);
    //List<Tarjeta> findByCodCuentaAndEstadoOrderByFechaUltimoCambio(Integer codCuent, String estado, Timestamp fechaUltimoCambio);
    Tarjeta findByNumero(String numero);
    

}
