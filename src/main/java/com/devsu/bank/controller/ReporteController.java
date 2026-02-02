package com.devsu.bank.controller;

import com.devsu.bank.dto.ReporteEstadoCuentaResponse;
import com.devsu.bank.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import com.devsu.bank.dto.ReporteEstadoCuentaPdfResponse;

@RestController
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // Opción A: query params
    @GetMapping("/reportes")
    public ReporteEstadoCuentaResponse reporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return reporteService.reporte(clienteId, fechaInicio, fechaFin);
    }

    // Opción B: path + query
    @GetMapping("/clientes/{clienteId}/reportes")
    public ReporteEstadoCuentaResponse reportePorCliente(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return reporteService.reporte(clienteId, fechaInicio, fechaFin);
    }


    @GetMapping("/clientes/{clienteId}/reportes/pdf")
    public ReporteEstadoCuentaPdfResponse reportePdfPorCliente(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return reporteService.reportePdf(clienteId, fechaInicio, fechaFin);
    }
}