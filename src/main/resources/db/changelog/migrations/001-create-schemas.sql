--liquibase formatted sql
--changeset salb:001-create-schemas

CREATE SCHEMA IF NOT EXISTS catalogo;
CREATE SCHEMA IF NOT EXISTS inventario;
