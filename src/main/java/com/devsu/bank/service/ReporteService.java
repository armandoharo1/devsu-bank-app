package com.devsu.bank.service;

import com.devsu.bank.dto.ReporteMovimientoResponse;
import com.devsu.bank.exception.BusinessException;
import com.devsu.bank.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final MovimientoRepository movimientoRepository;

    @Transactional(readOnly = true)
    public List<ReporteMovimientoResponse> reporte(
            Long clienteId,
            LocalDate fechaInicio,
            LocalDate fechaFin
    ) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new BusinessException("La fecha inicio no puede ser mayor a la fecha fin");
        }

        return movimientoRepository.reportePorClienteYRango(
                clienteId, fechaInicio, fechaFin
        );
    }
}