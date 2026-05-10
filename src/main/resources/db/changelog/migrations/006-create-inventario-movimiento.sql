--liquibase formatted sql
--changeset salb:006-create-inventario-movimiento

CREATE TABLE IF NOT EXISTS inventario.inventario_movimiento (
    id            UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    producto_id   UUID         NOT NULL,
    tipo          VARCHAR(10)  NOT NULL CHECK (tipo IN ('ENTRADA', 'SALIDA')),
    cantidad      INTEGER      NOT NULL CHECK (cantidad > 0),
    referencia    VARCHAR(100),
    registrado_en TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_inv_movimiento_producto_id ON inventario.inventario_movimiento (producto_id);
