package com.devsu.bank.service;

import com.devsu.bank.dto.MovimientoCreateRequest;
import com.devsu.bank.dto.MovimientoResponse;
import com.devsu.bank.entity.Cuenta;
import com.devsu.bank.entity.Movimiento;
import com.devsu.bank.exception.BusinessException;
import com.devsu.bank.repository.CuentaRepository;
import com.devsu.bank.repository.MovimientoRepository;
import com.devsu.bank.service.strategy.CreditoStrategy;
import com.devsu.bank.service.strategy.DebitoStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    private final CreditoStrategy creditoStrategy;
    private final DebitoStrategy debitoStrategy;

    @Transactional
    public MovimientoResponse crearMovimiento(MovimientoCreateRequest req) {
        Cuenta cuenta = cuentaRepository.findById(req.numeroCuenta())
                .orElseThrow(() -> new BusinessException("Cuenta no encontrada"));

        LocalDate hoy = LocalDate.now();

        Movimiento movimiento = switch (req.tipoMovimiento()) {
            case CREDITO -> creditoStrategy.apply(cuenta, req.valor(), hoy);
            case DEBITO -> debitoStrategy.apply(cuenta, req.valor(), hoy);
        };

        // Guardamos el movimiento
        Movimiento saved = movimientoRepository.save(movimiento);

        cuenta.setSaldoDisponible(saved.getSaldo());
        cuentaRepository.save(cuenta);

        // Convertimos Entity -> DTO
        return new MovimientoResponse(
                getMovimientoId(saved),
                saved.getFecha(),
                saved.getTipoMovimiento().name(),
                saved.getValor(),
                saved.getSaldo(),
                saved.getCuenta().getNumeroCuenta()
        );
    }

    private Long getMovimientoId(Movimiento m) {
        return m.getId();
    }
}