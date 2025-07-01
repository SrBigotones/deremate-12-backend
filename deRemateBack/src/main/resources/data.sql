INSERT INTO USUARIO(USERNAME, PASSWORD, EMAIL, NOMBRE, APELLIDO, DOCUMENTO, ESTADO) VALUES
   ('user1', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user1@test.com', 'Usuario', 'Uno', 33333333, 'ACTIVO'),
   ('user2', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user2@test.com', 'Usuario', 'Dos', 34444444, 'ACTIVO'),
   ('user3', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user3@test.com', 'Usuario', 'Tres', 35555555, 'ACTIVO'),
   ('user4', '$2a$10$bhoNyi0sxaGQu.50zOiUL.SKpSPGHytwGruHhYYP2hSDZpkYHEIri', 'user4@test.com', 'Usuario', 'Cuatro', 36666666, 'ACTIVO');


INSERT INTO ENTREGA (ESTADO, FECHA_CREACION, FECHA_ENTREGA, USUARIO_ID, DIRECCION_ENTREGA, OBSERVACIONES, COMENTARIO, CALIFICACION, IMAGEN, EMAIL_CLIENTE) VALUES
(0, '2025-04-19 14:23:45.123456', null, 1, 'Lima 757, C1073 Cdad. Autónoma de Buenos Aires', 'Test creacion', null, null, 'https://confilegal.com/wp-content/uploads/2016/01/paquete-postal-copy.jpg', 'cliente1@test.com'),
(3, '2025-04-19 14:23:45.123456', null, 1, 'Lima 757, C1073 Cdad. Autónoma de Buenos Aires', 'Test creacion', null, null, 'https://i.pinimg.com/736x/e1/0a/ec/e10aec14dff8b311d893994a270da521.jpg', 'cliente2@test.com'),
(2, '2025-04-19 14:23:45.123456', null, 1, 'Av. Córdoba, C1041 Cdad. Autónoma de Buenos Aires', 'lorem ip', 'Todo bien, pero tardó un poco.', 3, 'https://i.pinimg.com/736x/66/19/cf/6619cfae3b0b5fc0dff866b3a06f6f52.jpg', 'cliente1@test.com'),
(1, '2025-04-20 14:23:45.123456', null, 1, 'Lavalle 3201-3299, C1190AAK Cdad. Autónoma de Buenos Aires', 'jojoj jejeje', null, null, 'https://i.pinimg.com/736x/70/66/73/706673f96aeb12e4e3403dec455588c6.jpg', 'cliente1@test.com'),
(2, '2025-04-21 10:15:30.000000', '2025-04-21 13:00:00.000000', 1, 'Av. Santa Fe 1860, C1123 Cdad. Autónoma de Buenos Aires', 'Entrega en horario de la tarde', 'Excelente servicio, llegó puntual.', 5, 'https://i.pinimg.com/736x/59/73/a6/5973a64d5e06fd6c3679ccdc81cecdab.jpg', 'cliente4@test.com'),
(1, '2025-04-22 08:45:00.000000', null, 1, 'Av. Rivadavia 6800, C1406 Cdad. Autónoma de Buenos Aires', 'Cliente solicita llamada previa', null, null, 'https://i.pinimg.com/736x/95/ad/ce/95adcebe7c3c79ee9600ad76388d415a.jpg', 'cliente1@test.com'),
(2, '2025-04-23 12:00:00.000000', '2025-04-23 15:45:00.000000', 1, 'Av. Corrientes 3480, C1193 Cdad. Autónoma de Buenos Aires', 'Departamento 3B, timbre roto', 'Tuve que bajar a abrir, pero el chico fue cordial.', 4, 'https://i.pinimg.com/736x/8c/f6/e6/8cf6e64fa37fa94ae5543a742c3e9e16.jpg', 'cliente1@test.com'),
(2, '2025-04-24 14:10:00.000000', '2025-04-24 16:00:00.000000', 1, 'Juramento 2089, C1428 Cdad. Autónoma de Buenos Aires', 'Entregar en portería', 'Perfecto, dejaron en portería como pedí.', 5, 'https://i.pinimg.com/736x/fa/c8/ff/fac8ffe302624f9ce9db8750703dedd4.jpg', 'cliente1@test.com');

