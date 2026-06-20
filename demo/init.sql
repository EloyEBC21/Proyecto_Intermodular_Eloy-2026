-- 1. Crear la base de datos (por si acaso no existe ya)
CREATE DATABASE IF NOT EXISTS tienda_db;

-- 2. Asegurarse de usar esa base de datos
USE tienda_db;

-- 3. Crear un usuario específico para tu aplicación Spring
-- Cambia 'tu_password' por la contraseña que quieras usar en tu application.properties
CREATE USER IF NOT EXISTS 'admin_app'@'%' IDENTIFIED BY 'tu_password';

-- 4. Darle todos los privilegios sobre la base de datos de tu proyecto
GRANT ALL PRIVILEGES ON tienda_db.* TO 'admin_app'@'%';

-- 5. Aplicar los cambios de permisos inmediatamente
FLUSH PRIVILEGES;
