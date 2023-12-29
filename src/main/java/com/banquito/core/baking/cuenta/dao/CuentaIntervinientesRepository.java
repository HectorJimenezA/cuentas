package com.banquito.core.baking.cuenta.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.baking.cuenta.domain.CuentaIntervinientes;
import com.banquito.core.baking.cuenta.domain.CuentaIntervinientesPK;


@Repository
public interface CuentaIntervinientesRepository extends CrudRepository<CuentaIntervinientes, CuentaIntervinientesPK> {

    //List<CuentaIntervinientes> findByFechaInicio(Timestamp fechaInicio, CuentaIntervinientesPK id);
}
