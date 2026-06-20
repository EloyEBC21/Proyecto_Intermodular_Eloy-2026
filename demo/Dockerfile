# Etapa 1: Compilación
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
# Copiamos primero solo el pom.xml para aprovechar la caché de capas de Docker
COPY pom.xml .
RUN mvn dependency:go-offline
# Ahora copiamos el código fuente y recursos
COPY src ./src
# Forzamos una limpieza antes del empaquetado
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# Copiamos el jar generado
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
# Exponemos el puerto
EXPOSE 8080
# Ejecutamos con un parámetro adicional para asegurar que lea la config externa si fuera necesario
ENTRYPOINT ["java", "-jar", "app.jar"]
