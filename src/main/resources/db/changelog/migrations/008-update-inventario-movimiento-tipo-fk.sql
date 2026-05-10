--liquibase formatted sql
--changeset salb:008-update-inventario-movimiento-tipo-fk

ALTER TABLE inventario.inventario_movimiento
    ADD COLUMN tipo_movimiento_id UUID;

UPDATE inventario.inventario_movimiento m
SET tipo_movimiento_id = t.id
FROM inventario.tipo_movimiento t
WHERE t.codigo = m.tipo;

ALTER TABLE inventario.inventario_movimiento
    ALTER COLUMN tipo_movimiento_id SET NOT NULL,
    ADD CONSTRAINT fk_inv_movimiento_tipo
        FOREIGN KEY (tipo_movimiento_id) REFERENCES inventario.tipo_movimiento (id);

ALTER TABLE inventario.inventario_movimiento
    DROP COLUMN tipo;
