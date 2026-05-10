--liquibase formatted sql
--changeset salb:002-create-categorias

CREATE TABLE IF NOT EXISTS catalogo.categorias (
    id               UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre           VARCHAR(100)             NOT NULL,
    descripcion      TEXT,
    activo           BOOLEAN                  NOT NULL DEFAULT TRUE,
    creado_en        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    actualizado_en   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_categorias_activo ON catalogo.categorias (activo);
