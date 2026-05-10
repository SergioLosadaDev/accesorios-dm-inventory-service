--liquibase formatted sql
--changeset salb:009-update-inventario-movimiento-auditoria

ALTER TABLE inventario.inventario_movimiento
    RENAME COLUMN referencia TO motivo;

ALTER TABLE inventario.inventario_movimiento
    ALTER COLUMN motivo TYPE VARCHAR(200);

ALTER TABLE inventario.inventario_movimiento
    ADD COLUMN responsable_id UUID;
