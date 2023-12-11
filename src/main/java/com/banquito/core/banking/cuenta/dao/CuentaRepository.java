package com.banquito.core.banking.cuenta.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.banking.cuenta.domain.Cuenta;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Integer>{
    Cuenta findByNumeroCuenta (String numeroCuenta);
    List<Cuenta> findByCodClienteAndEstado (Integer codCliente, String estado);
    List<Cuenta> findByCodCliente(Integer codCliente);

}