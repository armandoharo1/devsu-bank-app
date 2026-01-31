-- ===========================
-- DEVSU BANK - BaseDatos.sql
-- ===========================

CREATE TABLE IF NOT EXISTS persona (
  persona_id BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  genero VARCHAR(20) NOT NULL,
  edad INT NOT NULL CHECK (edad >= 0),
  identificacion VARCHAR(30) NOT NULL UNIQUE,
  direccion VARCHAR(200) NOT NULL,
  telefono VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente (
  persona_id BIGINT PRIMARY KEY,
  client_key VARCHAR(40) NOT NULL UNIQUE,
  password_hash VARCHAR(200) NOT NULL,
  estado BOOLEAN NOT NULL,
  CONSTRAINT fk_cliente_persona
    FOREIGN KEY (persona_id) REFERENCES persona(persona_id)
);

CREATE TABLE IF NOT EXISTS cuenta (
  numero_cuenta VARCHAR(30) PRIMARY KEY,
  tipo_cuenta VARCHAR(30) NOT NULL,
  saldo_inicial NUMERIC(14,2) NOT NULL CHECK (saldo_inicial >= 0),
  estado BOOLEAN NOT NULL,
  cliente_id BIGINT NOT NULL,
  CONSTRAINT fk_cuenta_cliente
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);

CREATE TABLE IF NOT EXISTS movimiento (
  movimiento_id BIGSERIAL PRIMARY KEY,
  fecha DATE NOT NULL,
  tipo_movimiento VARCHAR(10) NOT NULL CHECK (tipo_movimiento IN ('CREDITO','DEBITO')),
  valor NUMERIC(14,2) NOT NULL,
  saldo NUMERIC(14,2) NOT NULL,
  numero_cuenta VARCHAR(30) NOT NULL,
  CONSTRAINT fk_movimiento_cuenta
    FOREIGN KEY (numero_cuenta) REFERENCES cuenta(numero_cuenta)
);

CREATE INDEX IF NOT EXISTS idx_mov_cuenta_fecha
  ON movimiento(numero_cuenta, fecha);

CREATE INDEX IF NOT EXISTS idx_mov_cuenta_tipo_fecha
  ON movimiento(numero_cuenta, tipo_movimiento, fecha);