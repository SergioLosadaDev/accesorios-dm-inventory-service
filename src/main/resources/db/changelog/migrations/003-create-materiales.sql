--liquibase formatted sql
--changeset salb:003-create-materiales

CREATE TABLE IF NOT EXISTS catalogo.materiales (
    id               UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre           VARCHAR(100)             NOT NULL,
    descripcion      TEXT,
    activo           BOOLEAN                  NOT NULL DEFAULT TRUE,
    creado_en        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    actualizado_en   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_materiales_activo ON catalogo.materiales (activo);
