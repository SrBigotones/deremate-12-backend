INSERT INTO USUARIO(USERNAME, PASSWORD, EMAIL, NOMBRE, APELLIDO, DOCUMENTO, ESTADO) VALUES
   ('user1', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user1@test.com', 'Usuario', 'Uno', 33333333, 'ACTIVO'),
   ('user2', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user2@test.com', 'Usuario', 'Dos', 34444444, 'ACTIVO'),
   ('user3', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user3@test.com', 'Usuario', 'Tres', 35555555, 'ACTIVO'),
   ('user4', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user4@test.com', 'Usuario', 'Cuatro', 36666666, 'ACTIVO');


INSERT INTO ENTREGA(ESTADO, FECHA_CREACION, FECHA_ENTREGA, USUARIO_ID, DIRECCION_ENTREGA, OBSERVACIONES) VALUES
    (1, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Lima 757, C1073 Cdad. Autónoma de Buenos Aires', 'Test creacion'),
    (4, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Lima 757, C1073 Cdad. Autónoma de Buenos Aires', 'Test creacion'),
    (1, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 2, 'Av. Córdoba, C1041 Cdad. Autónoma de Buenos Aires', 'lorem ip'),
    (2, '2025-04-20 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Lavalle 3201-3299, C1190AAK Cdad. Autónoma de Buenos Aires', 'jojoj jejeje');

INSERT INTO ENTREGA(ESTADO, FECHA_CREACION, FECHA_ENTREGA, USUARIO_ID, DIRECCION_ENTREGA, OBSERVACIONES) VALUES
    (3, '2025-04-21 10:15:30.000000', '2025-04-21 13:00:00.000000', 1, 'Av. Santa Fe 1860, C1123 Cdad. Autónoma de Buenos Aires', 'Entrega en horario de la tarde'),
    (2, '2025-04-22 08:45:00.000000', '2025-04-22 09:30:00.000000', 1, 'Av. Rivadavia 6800, C1406 Cdad. Autónoma de Buenos Aires', 'Cliente solicita llamada previa'),
    (3, '2025-04-23 12:00:00.000000', '2025-04-23 15:45:00.000000', 1, 'Av. Corrientes 3480, C1193 Cdad. Autónoma de Buenos Aires', 'Departamento 3B, timbre roto'),
    (3, '2025-04-24 14:10:00.000000', '2025-04-24 16:00:00.000000', 1, 'Juramento 2089, C1428 Cdad. Autónoma de Buenos Aires', 'Entregar en portería');
