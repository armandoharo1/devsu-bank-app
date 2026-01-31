package com.devsu.bank.controller;

import com.devsu.bank.dto.MovimientoCreateRequest;
import com.devsu.bank.dto.MovimientoResponse;
import com.devsu.bank.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse crear(@Valid @RequestBody MovimientoCreateRequest req) {
        return movimientoService.crearMovimiento(req);
    }
}