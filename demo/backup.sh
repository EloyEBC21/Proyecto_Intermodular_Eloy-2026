#!/bin/bash
# Configuración
FECHA=$(date +%Y-%m-%d_%H-%M-%S)
BACKUP_DIR="./backups"

# Crear directorio si no existe
mkdir -p $BACKUP_DIR

# Ejecutar el dump (reemplaza 'demo-db-1' por el nombre de tu contenedor si es distinto)
docker exec demo-db-1 mysqldump -u root -p'tu_contraseña' tienda_db > $BACKUP_DIR/db_backup_$FECHA.sql

# Comprimir el backup
gzip $BACKUP_DIR/db_backup_$FECHA.sql

echo "Backup realizado con éxito en $BACKUP_DIR/db_backup_$FECHA.sql.gz"
