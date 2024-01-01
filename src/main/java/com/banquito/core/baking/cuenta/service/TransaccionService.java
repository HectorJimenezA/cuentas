package com.banquito.core.baking.cuenta.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.CuentaRepository;
import com.banquito.core.baking.cuenta.dao.TransaccionRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.Transaccion;
import com.banquito.core.baking.cuenta.service.CuentaService;

import jakarta.transaction.Transactional;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public Optional<Transaccion> getById(Integer codTransaccion) {
        return this.transaccionRepository.findById(codTransaccion);
    }

    public TransaccionService(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
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
                System.out.println("Holaaaaaaaaaaaa" + transaccionBeneficiario.toString());

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

    @Transactional
    public Transaccion transferencia(Transaccion transaccion) {
        try {

            if ("TRE".equals(transaccion.getTipoTransaccion())) {
                System.out.println("Holaaaaaaaaaaaa" + transaccion.toString());
                Optional<Cuenta> cuentaOrigen = cuentaRepository.findById(transaccion.getCodCuentaOrigen());
                Optional<Cuenta> cuentaDestino = cuentaRepository.findById(transaccion.getCodCuentaDestino());

                if (cuentaOrigen.isPresent() && cuentaDestino.isPresent()) {
                    Cuenta cuentaO = cuentaOrigen.get();
                    Cuenta cuentaD = cuentaDestino.get();

                    cuentaO.setSaldoContable(
                            cuentaO.getSaldoContable().subtract(transaccion.getValorDebe()));
                    cuentaO.setSaldoContable(
                            cuentaO.getSaldoDisponible().subtract(transaccion.getValorDebe()));

                    cuentaD.setSaldoContable(
                            cuentaD.getSaldoContable().add(transaccion.getValorDebe()));
                    cuentaD.setSaldoContable(
                            cuentaD.getSaldoDisponible().add(transaccion.getValorDebe()));

                    cuentaRepository.save(cuentaO);
                    cuentaRepository.save(cuentaD);

                }
                transaccion.hashCode();
                return this.transaccionRepository.save(transaccion);
            } else {

                throw new RuntimeException("El tipo de cuenta no es compatible con depósitos");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException(
                    "Error en creacion de la transaccion: " + transaccion + ", Error: " + e, e);
        }

    }

    public List<Transaccion> BuscarPorCodigoCuenta(Integer codCuentaOrigen) {
        return this.transaccionRepository.findByCodCuentaOrigen(codCuentaOrigen);
    }

}
