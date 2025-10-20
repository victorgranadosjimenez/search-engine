# Etapa 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos los archivos de Maven y descargamos dependencias
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos solo el JAR construido desde la etapa de build
COPY --from=build /app/target/mi-app.jar app.jar

# Exponemos el puerto que usa Spring Boot
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
