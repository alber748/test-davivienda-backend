# Proyecto Backend - Test Davivienda

Este proyecto es una API CRUD que interactúa con una base de datos PostgreSQL y un servicio SOAP. A continuación se detallan los pasos para configurar y levantar el backend localmente usando Docker.

## Requisitos Previos

- [Docker](https://docs.docker.com/get-docker/) instalado en tu sistema.
- JDK 17
- Maven 3.9.x

## Configuración del Docker

Es posible que encuentres problemas al instalar JDK o Maven, por lo que es recomendable hacer un pull de las imágenes directamente:
```
docker pull openjdk:17-jdk-alpine
```
```
docker pull maven:3.9.4-eclipse-temurin-17-alpine
```
## Configurar la Base de Datos

```
docker-compose up -d
```
## Compilar y Levantar el Proyecto Backend

```
mvn clean install
```

Si prefieres puedes ejecutarlo usando docker de la siguiente manera

```
docker build -t test-davivienda-backend .
docker run -p 8080:8080 test-davivienda-backend
```