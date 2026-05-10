--liquibase formatted sql
--changeset salb:004-create-productos

CREATE TABLE IF NOT EXISTS catalogo.productos (
    id               UUID                      PRIMARY KEY DEFAULT gen_random_uuid(),
    sku              VARCHAR(50)               NOT NULL UNIQUE,
    nombre           VARCHAR(200)              NOT NULL,
    descripcion      TEXT,
    precio           NUMERIC(12, 2)            NOT NULL,
    categoria_id     UUID                      NOT NULL REFERENCES catalogo.categorias(id),
    material_id      UUID                      REFERENCES catalogo.materiales(id),
    activo           BOOLEAN                   NOT NULL DEFAULT TRUE,
    creado_en        TIMESTAMP WITH TIME ZONE  NOT NULL DEFAULT NOW(),
    actualizado_en   TIMESTAMP WITH TIME ZONE  NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_productos_activo       ON catalogo.productos (activo);
CREATE INDEX IF NOT EXISTS idx_productos_categoria    ON catalogo.productos (categoria_id);
CREATE INDEX IF NOT EXISTS idx_productos_material     ON catalogo.productos (material_id);
CREATE INDEX IF NOT EXISTS idx_productos_nombre       ON catalogo.productos USING gin (to_tsvector('spanish', nombre));
