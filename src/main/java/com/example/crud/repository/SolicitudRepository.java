package com.example.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crud.model.Solicitud;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    // Definimos el método personalizado para encontrar solicitudes por cliente
    List<Solicitud> findByCliente_Id(Long clienteId);
}