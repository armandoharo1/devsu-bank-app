package com.devsu.bank.service.strategy;

import com.devsu.bank.entity.Cuenta;
import com.devsu.bank.entity.Movimiento;
import com.devsu.bank.entity.TipoMovimiento;
import com.devsu.bank.exception.BusinessException;
import com.devsu.bank.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DebitoStrategy implements MovimientoStrategy {

    private static final BigDecimal LIMITE_DIARIO = new BigDecimal("1000.00");

    private final MovimientoRepository movimientoRepository;

    @Override
    public Movimiento apply(Cuenta cuenta, BigDecimal valor, LocalDate fecha) {
        BigDecimal retiro = valor.abs();

        // Cupo diario: suma de débitos del día, valor negativo guardado luego
        BigDecimal consumidoHoy = movimientoRepository
                .findByCuentaAndTipoMovimientoAndFecha(cuenta, TipoMovimiento.DEBITO, fecha)
                .stream()
                .map(m -> m.getValor().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (consumidoHoy.add(retiro).compareTo(LIMITE_DIARIO) > 0) {
            throw new BusinessException("Cupo diario Excedido");
        }

        // Saldo actual
        BigDecimal saldoActual = cuenta.getSaldoDisponible();

        if (saldoActual.compareTo(BigDecimal.ZERO) == 0 || saldoActual.compareTo(retiro) < 0) {
            throw new BusinessException("Saldo no disponible");
        }

        BigDecimal nuevoSaldo = saldoActual.subtract(retiro);

        return Movimiento.builder()
                .fecha(fecha)
                .tipoMovimiento(TipoMovimiento.DEBITO)
                .valor(retiro.negate())
                .saldo(nuevoSaldo)
                .cuenta(cuenta)
                .build();
    }
}