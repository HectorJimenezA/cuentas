package com.banquito.core.baking.cuenta.dto;

import com.banquito.core.baking.cuenta.domain.Cuenta;

public class CuentaBuilder {

    public static CuentaDTO toDTO(Cuenta cuenta){

        CuentaDTO dto = CuentaDTO.builder().codCliente(cuenta.getCodCliente()).codCuenta(cuenta.getCodCuenta()).codTipoCuenta(cuenta.getCodTipoCuenta()).estado(cuenta.getEstado()).fechaCreacion(cuenta.getFechaCreacion()).fechaUltimoCambio(cuenta.getFechaUltimoCambio()).numeroCuenta(cuenta.getNumeroCuenta()).saldoContable(cuenta.getSaldoContable()).saldoDisponible(cuenta.getSaldoDisponible()).build();
        return dto;
    }

    public static Cuenta toCuenta(CuentaDTO dto){

        Cuenta cuenta = new Cuenta();
        cuenta.setCodCliente(dto.getCodCliente());
        cuenta.setCodCuenta(dto.getCodCuenta());
        cuenta.setCodTipoCuenta(dto.getCodTipoCuenta());
        cuenta.setEstado(dto.getEstado());
        cuenta.setFechaCreacion(dto.getFechaCreacion());
        cuenta.setFechaUltimoCambio(dto.getFechaUltimoCambio());
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setSaldoContable(dto.getSaldoContable());
        cuenta.setSaldoDisponible(dto.getSaldoDisponible());
        return cuenta;

    }
}
