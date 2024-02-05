package com.banquito.core.baking.cuenta.dto;

import com.banquito.core.baking.cuenta.domain.TipoCuenta;

public class TipoCuentaBuilder {

    public static TipoCuentaDTO toDTO(TipoCuenta tipoCuenta){

        TipoCuentaDTO dto = TipoCuentaDTO.builder()
        .codTasaInteres(tipoCuenta.getCodTasaInteres())
        .codTipoCuenta(tipoCuenta.getCodTipoCuenta())
        .nombre(tipoCuenta.getNombre())
        .descripcion(tipoCuenta.getDescripcion())
        .tipoCapitalizacion(tipoCuenta.getTipoCapitalizacion())
        .formaCapitalizacion(tipoCuenta.getFormaCapitalizacion())
        .maximoNumeroIntervinientes(tipoCuenta.getMaximoNumeroIntervinientes())
        .fechaCreacion(tipoCuenta.getFechaCreacion())
        .fechaUltimoCambio(tipoCuenta.getFechaUltimoCambio())
        .build();
        return dto;
    }

    public static TipoCuenta toTipoCuenta(TipoCuentaDTO dto){

        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setCodTasaInteres(dto.getCodTasaInteres());
        tipoCuenta.setCodTipoCuenta(dto.getCodTasaInteres());
        tipoCuenta.setNombre(dto.getNombre());
        tipoCuenta.setDescripcion(dto.getDescripcion());
        tipoCuenta.setTipoCapitalizacion(dto.getTipoCapitalizacion());
        tipoCuenta.setFormaCapitalizacion(dto.getFormaCapitalizacion());
        tipoCuenta.setMaximoNumeroIntervinientes(dto.getMaximoNumeroIntervinientes());
        tipoCuenta.setFechaCreacion(dto.getFechaCreacion());
        tipoCuenta.setFechaUltimoCambio(dto.getFechaUltimoCambio());
        return tipoCuenta;

    }
}
