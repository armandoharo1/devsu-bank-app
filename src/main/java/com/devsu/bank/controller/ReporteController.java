package com.devsu.bank.controller;

import com.devsu.bank.dto.ReporteMovimientoResponse;
import com.devsu.bank.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // Opción A: query params
    @GetMapping("/reportes")
    public List<ReporteMovimientoResponse> reporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return reporteService.reporte(clienteId, fechaInicio, fechaFin);
    }

    // Opción B: path + query
    @GetMapping("/clientes/{clienteId}/reportes")
    public List<ReporteMovimientoResponse> reportePorCliente(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return reporteService.reporte(clienteId, fechaInicio, fechaFin);
    }
}