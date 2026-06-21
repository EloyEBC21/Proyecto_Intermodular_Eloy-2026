-- Limpieza total para evitar duplicados
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE usuarios;
TRUNCATE TABLE productos;
TRUNCATE TABLE partidas;
SET FOREIGN_KEY_CHECKS = 1;

-- ==========================================================
-- 1. USUARIOS
-- ==========================================================
INSERT INTO usuarios (usuario, email, password, edad, juegos) VALUES 
('Guillermo', 'guille@mail.com', '1234', 28, 'Dungeons & Dragons, Magic: The Gathering');

-- ==========================================================
-- 2. PARTIDAS
-- ==========================================================
INSERT INTO partidas (titulo, juego, fecha, lugar, jugadores_apuntados, jugadores_max) VALUES 
('Noche de D&D: La Mina Perdida', 'Dungeons & Dragons 5e', '2026-06-05 18:30:00', 'Game & Grill - Local', 4, 6),
('Torneo Catan', 'Los Colonos de Catán', '2026-06-07 17:00:00', 'Game & Grill - Local', 8, 12),
('Magic: The Gathering Commander', 'Magic MTG', '2026-06-10 19:15:00', 'Game & Grill - Local', 6, 8),
('Mesa libre: Juegos variados', 'Varios juegos disponibles', '2026-06-12 20:00:00', 'Game & Grill - Local', 3, 10);

-- ==========================================================
-- 3. PRODUCTOS DEL MENÚ
-- ==========================================================

-- ENTRANTES
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Nachos Supreme', 'Con queso, guacamole y jalapeños', 8.50, 'Entrantes'),
('Alitas de BBQ', '8 unidades con salsa BBQ', 9.00, 'Entrantes'),
('Tequeños', '6 unidades con salsa de miel y mostaza', 7.50, 'Entrantes'),
('Aros de Cebolla', 'Crujientes con salsa agridulce', 6.00, 'Entrantes');

-- ENSALADAS
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Ensalada César', 'Pollo, picatostes, parmesano y salsa César', 9.50, 'Ensaladas'),
('Ensalada Caprese', 'Tomate, mozzarella fresca y albahaca', 8.50, 'Ensaladas');

-- HAMBURGUESAS
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Hamburguesa Gamer', 'Doble carne, bacon, queso y patatas', 12.50, 'Hamburguesas'),
('Hamburguesa Veggie', 'Hamburguesa de garbanzos, aguacate y rúcula', 11.00, 'Hamburguesas'),
('Hamburguesa Doble Queso', 'Carne de ternera con triple queso fundido', 13.00, 'Hamburguesas');

-- PIZZAS
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Pizza D&D', 'Pizza familiar con ingredientes épicos', 15.00, 'Pizzas'),
('Pizza Margarita', 'Tomate, mozzarella y albahaca', 10.00, 'Pizzas'),
('Pizza Barbacoa', 'Carne picada, bacon y salsa barbacoa', 13.50, 'Pizzas');

-- BOCADILLOS
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Bocadillo Serrano', 'Jamón serrano, tomate y aceite de oliva', 6.50, 'Bocadillos'),
('Bocadillo de Pollo', 'Pechuga, lechuga, tomate y mayonesa', 7.00, 'Bocadillos');

-- BEBIDAS
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Coca-Cola', 'Refresco de cola bien frío', 2.50, 'Bebidas'),
('Cerveza Artesana', 'Cerveza local de estilo IPA', 4.00, 'Bebidas'),
('Agua Mineral', 'Botella de 50cl', 1.50, 'Bebidas');

-- SALSAS
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Salsa Barbacoa', 'Salsa casera ahumada', 0.50, 'Salsas'),
('Alioli', 'Ajo suave y aceite', 0.50, 'Salsas');

-- POSTRES
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES 
('Brownie de Chocolate', 'Con helado de vainilla y nueces', 5.50, 'Postres'),
('Tarta de Queso', 'Casera con mermelada de frutos rojos', 5.00, 'Postres'),
('Helado de Vainilla', 'Dos bolas de helado artesano', 4.00, 'Postres');
