--liquibase formatted sql
--changeset salb:007-create-tipo-movimiento

CREATE TABLE IF NOT EXISTS inventario.tipo_movimiento (
    id     UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL
);

INSERT INTO inventario.tipo_movimiento (codigo, nombre) VALUES
    ('ENTRADA', 'Entrada'),
    ('SALIDA',  'Salida');
