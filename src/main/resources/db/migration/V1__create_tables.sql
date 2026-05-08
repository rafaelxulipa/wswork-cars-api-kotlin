CREATE TABLE marca (
    id   BIGSERIAL PRIMARY KEY,
    nome_marca VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE modelo (
    id         BIGSERIAL PRIMARY KEY,
    marca_id   BIGINT      NOT NULL REFERENCES marca(id),
    nome       VARCHAR(100) NOT NULL,
    valor_fipe NUMERIC(12, 2) NOT NULL DEFAULT 0
);

CREATE TABLE carro (
    id                  BIGSERIAL PRIMARY KEY,
    timestamp_cadastro  BIGINT       NOT NULL,
    modelo_id           BIGINT       NOT NULL REFERENCES modelo(id),
    ano                 INTEGER      NOT NULL,
    combustivel         VARCHAR(20)  NOT NULL,
    num_portas          INTEGER      NOT NULL,
    cor                 VARCHAR(30)  NOT NULL
);
