package com.devsu.bank.repository;

import com.devsu.bank.entity.Cuenta;
import com.devsu.bank.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    List<Cuenta> findByCliente(Cliente cliente);

    boolean existsByNumeroCuenta(String numeroCuenta);
}