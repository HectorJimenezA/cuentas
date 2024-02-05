package com.banquito.core.baking.cuenta.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.banquito.core.baking.cuenta.dao.CuentaRepository;
import com.banquito.core.baking.cuenta.dao.TransaccionRepository;
import com.banquito.core.baking.cuenta.domain.Cuenta;
import com.banquito.core.baking.cuenta.domain.Transaccion;
import com.banquito.core.baking.cuenta.dto.TransaccionBuilder;
import com.banquito.core.baking.cuenta.dto.TransaccionDTO;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionService(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public TransaccionDTO getById(Integer codTransaccion) {
        Transaccion transaccion = this.transaccionRepository.findById(codTransaccion).get();
        log.info("Obteniendo la transacción con id: {}", codTransaccion);
        return TransaccionBuilder.toDTO(transaccion);
    }

    

    @Transactional
    public void crear(TransaccionDTO dto) {
        try {          
            Transaccion transaccion = TransaccionBuilder.toTransaccion(dto);
            log.info("Se creo la transacción: {}", transaccion);
            this.transaccionRepository.save(transaccion);

        } catch (Exception e) {
            
            throw new CreacionException("Error en creacion de la transaccion: ", e);
        }
    }

  
    @Transactional
    public Transaccion depositar(String numCuenta, BigDecimal valorDebe, Timestamp fecha) {
        try {
            log.info("Iniciando el proceso de depósito en la cuenta con número: {}", numCuenta);
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

                cuentaRepository.save(cuentaBeneficiario);
                log.info("Depósito realizado con éxito. Transacción creada: {}", transaccion);
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
            log.info("Iniciando el proceso de retiro en la cuenta con número: {}", numCuenta);
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

                    cuentaRepository.save(cuentaPropietario);
                    log.info("Retiro realizado con éxito. Transacción creada: {}", transaccion);
                    return this.transaccionRepository.save(transaccion);
                } else {
                    throw new RuntimeException("No posee fondos suficientes");
                }

            } else {

                throw new RuntimeException("No existe el número de cuenta ingresado");
            }
        } catch (Exception e) {
            
            throw new CreacionException(
                    "Error en creacion de la transaccion:  Error: " + e, e);
        }

    }

    @Transactional
    public Transaccion transferencia(Transaccion transaccion) {
        try {
            log.info("Iniciando el proceso de transferencia con la transacción: {}", transaccion);

            if ("TRE".equals(transaccion.getTipoTransaccion()) || "TEN".equals(transaccion.getTipoTransaccion())) {
                System.out.println("Holaaaaaaaaaaaa" + transaccion.toString());
                Optional<Cuenta> cuentaOrigen = cuentaRepository.findById(transaccion.getCodCuentaOrigen());
                Optional<Cuenta> cuentaDestino = cuentaRepository.findById(transaccion.getCodCuentaDestino());

                if (cuentaOrigen.isPresent() && cuentaDestino.isPresent()) {
                    Cuenta cuentaO = cuentaOrigen.get();
                    Cuenta cuentaD = cuentaDestino.get();

                    cuentaO.setSaldoContable(
                            cuentaO.getSaldoContable().subtract(transaccion.getValorDebe()));
                    cuentaO.setSaldoDisponible(
                            cuentaO.getSaldoDisponible().subtract(transaccion.getValorDebe()));

                    cuentaD.setSaldoContable(
                            cuentaD.getSaldoContable().add(transaccion.getValorDebe()));
                    cuentaD.setSaldoDisponible(
                            cuentaD.getSaldoDisponible().add(transaccion.getValorDebe()));

                    cuentaRepository.save(cuentaO);
                    cuentaRepository.save(cuentaD);

                    log.info("Transferencia realizada con éxito. Cuenta origen: {}, Cuenta destino: {}, Monto: {}",
                        cuentaO.getNumeroCuenta(), cuentaD.getNumeroCuenta(), transaccion.getValorDebe());

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

    public List<TransaccionDTO> BuscarPorCodigoCuenta(Integer codCuentaOrigen) {
        log.info("Buscando transacciones por código de cuenta de origen: {}", codCuentaOrigen);
        List<TransaccionDTO> dtos = new ArrayList<>();
        for (Transaccion transaccion : this.transaccionRepository.findByCodCuentaOrigen(codCuentaOrigen)){
            dtos.add(TransaccionBuilder.toDTO(transaccion));
        }
        return dtos;
    }

}
