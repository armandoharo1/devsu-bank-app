package com.devsu.bank.dto;

import com.devsu.bank.entity.TipoMovimiento; // ajusta el paquete real del enum
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReporteMovimientoResponse(
        LocalDate fecha,
        String cliente,
        String numeroCuenta,
        TipoMovimiento tipo,     // ✅ antes String
        BigDecimal saldoInicial,
        Boolean estado,
        BigDecimal movimiento,
        BigDecimal saldoDisponible
) {}