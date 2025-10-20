# Usamos la imagen oficial de OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Creamos un directorio dentro del contenedor
WORKDIR /app

# Copiamos el JAR compilado al contenedor
COPY target/mi-app.jar app.jar

# Exponemos el puerto de la aplicación (el que usa Spring Boot)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
