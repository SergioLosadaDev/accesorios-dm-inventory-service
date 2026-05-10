--liquibase formatted sql
--changeset salb:005-create-imagenes-producto

CREATE TABLE IF NOT EXISTS catalogo.imagenes_producto (
    id           UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    producto_id  UUID         NOT NULL REFERENCES catalogo.productos(id) ON DELETE CASCADE,
    url          VARCHAR(500) NOT NULL,
    es_principal BOOLEAN      NOT NULL DEFAULT FALSE,
    orden        INTEGER      NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_imagenes_producto_id ON catalogo.imagenes_producto (producto_id);
