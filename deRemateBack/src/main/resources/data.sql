INSERT INTO USUARIO(USERNAME, PASSWORD, EMAIL, NOMBRE, APELLIDO, DOCUMENTO) VALUES
   ('user1', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user1@test.com', 'Usuario', 'Uno', 33333333),
   ('user2', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user2@test.com', 'Usuario', 'Dos', 34444444),
   ('user3', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user3@test.com', 'Usuario', 'Tres', 35555555),
   ('user4', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user4@test.com', 'Usuario', 'Cuatro', 36666666);


INSERT INTO ENTREGA(ESTADO, FECHA_CREACION, FECHA_ENTREGA, USUARIO_ID, DIRECCION_ENTREGA, OBSERVACIONES) VALUES
    (1, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Falsa 123', ''),
    (1, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 2, 'Falsa 123', ''),
    (2, '2025-04-20 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Falsa 123', '');