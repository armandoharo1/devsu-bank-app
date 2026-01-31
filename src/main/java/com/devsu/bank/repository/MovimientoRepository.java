package com.devsu.bank.repository;

import com.devsu.bank.entity.Movimiento;
import com.devsu.bank.entity.Cuenta;
import com.devsu.bank.entity.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaAndFechaBetween(
            Cuenta cuenta,
            LocalDate desde,
            LocalDate hasta
    );

    List<Movimiento> findByCuentaAndFecha(
            Cuenta cuenta,
            LocalDate fecha
    );

    List<Movimiento> findByCuentaAndTipoMovimientoAndFecha(
            Cuenta cuenta,
            TipoMovimiento tipoMovimiento,
            LocalDate fecha
    );
}