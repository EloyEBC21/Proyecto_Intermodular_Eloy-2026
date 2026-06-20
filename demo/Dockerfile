# Etapa 1: Compilación
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
# Descarga dependencias sin instalar nada extra
RUN mvn dependency:go-offline
COPY src ./src
# Limpieza total y empaquetado sin tests
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# Copiamos el jar
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
# Exponemos el puerto
EXPOSE 8080

# ENTRYPOINT: Usamos los parámetros de Spring Boot para forzar la configuración externa
# Esto asegura que si falta el archivo externo, la app no encuentre la BD y falle (como debe ser)
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/application.properties"]