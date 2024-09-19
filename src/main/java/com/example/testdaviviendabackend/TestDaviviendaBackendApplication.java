package com.example.testdaviviendabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.crud", "com.example.soap"})  // Ajusta los paquetes
@EntityScan(basePackages = "com.example.crud.model")  // Ajusta para tus entidades
@EnableJpaRepositories(basePackages = "com.example.crud.repository")  // Ajusta para tus repositorios
public class TestDaviviendaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDaviviendaBackendApplication.class, args);
    }
}