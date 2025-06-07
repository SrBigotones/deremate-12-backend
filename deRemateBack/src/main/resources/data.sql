INSERT INTO USUARIO(USERNAME, PASSWORD, EMAIL, NOMBRE, APELLIDO, DOCUMENTO, ESTADO) VALUES
   ('user1', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user1@test.com', 'Usuario', 'Uno', 33333333, 'ACTIVO'),
   ('user2', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user2@test.com', 'Usuario', 'Dos', 34444444, 'ACTIVO'),
   ('user3', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user3@test.com', 'Usuario', 'Tres', 35555555, 'ACTIVO'),
   ('user4', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user4@test.com', 'Usuario', 'Cuatro', 36666666, 'ACTIVO');


INSERT INTO ENTREGA(ESTADO, FECHA_CREACION, FECHA_ENTREGA, USUARIO_ID, DIRECCION_ENTREGA, DIRECCION_DEPOSITO, OBSERVACIONES) VALUES
    (1, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Lima 757, C1073 Cdad. Autónoma de Buenos Aires', 'Deposito 1, Paquete 32 Seccion A', 'Test creacion'),
    (4, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Lima 757, C1073 Cdad. Autónoma de Buenos Aires', 'Deposito 1, Paquete 32 Seccion A', 'Test creacion'),
    (1, '2025-04-19 14:23:45.123456', '2025-04-19 14:23:45.123456', 2, 'Av. Córdoba, C1041 Cdad. Autónoma de Buenos Aires', 'Deposito 1, Paquete 32 Seccion A', 'lorem ip'),
    (2, '2025-04-20 14:23:45.123456', '2025-04-19 14:23:45.123456', 1, 'Lavalle 3201-3299, C1190AAK Cdad. Autónoma de Buenos Aires', 'Deposito 1, Paquete 32 Seccion A', 'jojoj jejeje');