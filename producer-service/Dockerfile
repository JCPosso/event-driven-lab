# producer-service/Dockerfile
# Usa una imagen base de JRE ligera que coincida con tu JDK de compilacion
FROM openjdk:17-jdk-slim

# Argumento para el JAR (se pasa durante la construccion en docker-compose)
ARG JAR_FILE=target/*.jar

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR construido a la imagen
COPY ${JAR_FILE} app.jar

# Expone el puerto de la aplicacion
EXPOSE 8080

# Comando para ejecutar la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]
