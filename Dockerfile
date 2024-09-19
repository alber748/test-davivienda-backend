# Etapa 1: Construcción de la aplicación con Maven
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/test-davivienda-backend-0.0.1-SNAPSHOT.jar /app/dav-test-back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/dav-test-back.jar"]