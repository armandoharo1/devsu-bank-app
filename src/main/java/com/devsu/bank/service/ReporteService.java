package com.devsu.bank.service;

import com.devsu.bank.dto.ReporteMovimientoResponse;
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
    public List<ReporteMovimientoResponse> reporte(Long clienteId, LocalDate inicio, LocalDate fin) {
        return movimientoRepository.reportePorClienteYRango(clienteId, inicio, fin);
    }
}