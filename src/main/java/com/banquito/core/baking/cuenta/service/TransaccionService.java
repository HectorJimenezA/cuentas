package com.banquito.core.baking.cuenta.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.banquito.core.baking.cuenta.dao.CuentaRepository;
import com.banquito.core.baking.cuenta.dao.TransaccionRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.Transaccion;
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
    public Transaccion depositar(String numCuenta, BigDecimal valorDebe, Timestamp fecha) {
        try {

            int longitud = 64;
            String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder codigoUnicoGenerado = new StringBuilder(longitud);
            Transaccion transaccion = new Transaccion();

            if (cuentaRepository.findByNumeroCuenta(numCuenta) != null) {
                Cuenta cuentaBeneficiario = cuentaRepository.findByNumeroCuenta(numCuenta);

                cuentaBeneficiario.setSaldoContable(cuentaBeneficiario.getSaldoContable().add(valorDebe));
                cuentaBeneficiario.setSaldoDisponible(cuentaBeneficiario.getSaldoDisponible().add(valorDebe));
                cuentaBeneficiario.setFechaUltimoCambio(fecha);

                transaccion.setCodCuentaDestino(cuentaBeneficiario.getCodCuenta());

                for (int i = 0; i < longitud; i++) {
                    int index = random.nextInt(caracteres.length());
                    codigoUnicoGenerado.append(caracteres.charAt(index));
                }
                transaccion.setCodUnico(codigoUnicoGenerado.toString());
                transaccion.setTipoAfectacion("D");
                transaccion.setValorDebe(valorDebe);
                transaccion.setValorHaber(valorDebe.subtract(valorDebe));
                transaccion.setTipoTransaccion("DEP");
                transaccion.setDetalle("DEPOSITO BANCARIO");
                transaccion.setFechaCreacion(fecha);
                transaccion.setEstado("EXI");
                transaccion.setFechaUltimoCambio(fecha);
                transaccion.setVersion(1L);

                cuentaRepository.save(cuentaBeneficiario);///////////////////// ojo poner al final

                return this.transaccionRepository.save(transaccion);

            } else {

                throw new RuntimeException("No existe el número de cuenta ingresado");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException(
                    "Error en creacion de la transaccion:  Error: " + e, e);
        }

    }

    @Transactional
    public Transaccion retirar(String numCuenta, BigDecimal valorHaber, Timestamp fecha) {
        try {

            int longitud = 64;
            String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder codigoUnicoGenerado = new StringBuilder(longitud);
            Transaccion transaccion = new Transaccion();

            if (cuentaRepository.findByNumeroCuenta(numCuenta) != null) {
                Cuenta cuentaPropietario = cuentaRepository.findByNumeroCuenta(numCuenta);

                if (cuentaPropietario.getSaldoDisponible().compareTo(valorHaber) > 0) {
                    cuentaPropietario.setSaldoContable(cuentaPropietario.getSaldoContable().subtract(valorHaber));
                    cuentaPropietario.setSaldoDisponible(cuentaPropietario.getSaldoDisponible().subtract(valorHaber));
                    cuentaPropietario.setFechaUltimoCambio(fecha);

                    transaccion.setCodCuentaOrigen(cuentaPropietario.getCodCuenta());

                    for (int i = 0; i < longitud; i++) {
                        int index = random.nextInt(caracteres.length());
                        codigoUnicoGenerado.append(caracteres.charAt(index));
                    }
                    transaccion.setCodUnico(codigoUnicoGenerado.toString());
                    transaccion.setTipoAfectacion("C");
                    transaccion.setValorDebe(valorHaber.subtract(valorHaber));
                    transaccion.setValorHaber(valorHaber);
                    transaccion.setTipoTransaccion("RET");
                    transaccion.setDetalle("RETIRO");
                    transaccion.setFechaCreacion(fecha);
                    transaccion.setEstado("EXI");
                    transaccion.setFechaUltimoCambio(fecha);
                    transaccion.setVersion(1L);

                    cuentaRepository.save(cuentaPropietario);///////////////////// ojo poner al final

                    return this.transaccionRepository.save(transaccion);
                } else {
                    throw new RuntimeException("No posee fondos suficientes");
                }

            } else {

                throw new RuntimeException("No existe el número de cuenta ingresado");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new CreacionException(
                    "Error en creacion de la transaccion:  Error: " + e, e);
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
