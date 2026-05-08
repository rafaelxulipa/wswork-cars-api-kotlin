INSERT INTO marca (nome_marca) VALUES
    ('Toyota'),
    ('Volkswagen'),
    ('Chevrolet'),
    ('Ford'),
    ('Fiat');

INSERT INTO modelo (marca_id, nome, valor_fipe) VALUES
    (1, 'Corolla', 120000.00),
    (1, 'Etios',   36000.00),
    (1, 'Hilux SW4', 47500.00),
    (2, 'Jetta',   49000.00),
    (2, 'Gol',     35000.00),
    (3, 'Onix Plus', 50000.00),
    (3, 'Tracker', 98000.00),
    (4, 'Ranger',  185000.00),
    (5, 'Toro',    120000.00);

INSERT INTO carro (timestamp_cadastro, modelo_id, ano, combustivel, num_portas, cor) VALUES
    (1696549488, 1, 2014, 'FLEX',     4, 'BRANCA'),
    (1696531236, 1, 2022, 'FLEX',     4, 'PRETA'),
    (1696535432, 3, 1993, 'DIESEL',   4, 'AZUL'),
    (1696539488, 6, 2015, 'FLEX',     4, 'BEGE'),
    (1696531234, 4, 2014, 'FLEX',     4, 'AZUL');
