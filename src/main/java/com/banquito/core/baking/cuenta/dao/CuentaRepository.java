package com.banquito.core.baking.cuenta.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.baking.cuenta.domain.Cuenta;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Integer>{
    

}