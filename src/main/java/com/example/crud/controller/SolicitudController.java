package com.example.crud.controller;

import com.example.crud.model.Cliente;
import com.example.crud.model.FormaPago;
import com.example.crud.model.Solicitud;
import com.example.crud.repository.ClienteRepository;
import com.example.crud.repository.FormaPagoRepository;
import com.example.crud.repository.SolicitudRepository;
import com.example.crud.service.ClienteService;
import com.example.crud.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private ClienteService clienteService;  // Para gestionar clientes
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private FormaPagoRepository formaPagoRepository;

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Solicitud> getAllSolicitudes() {
        return solicitudService.getAllSolicitudes();
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> getSolicitudById(@PathVariable Long id) {
        Optional<Solicitud> solicitud = solicitudService.getSolicitudById(id);
        return solicitud.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<?> createSolicitud(@PathVariable Long clienteId, @RequestBody Solicitud solicitud) {
        // Verificar si el cliente existe
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            solicitud.setCliente(cliente);

            // Validar y asignar la forma de pago si está presente
            if (solicitud.getFormaPago() != null) {
                FormaPago formaPago = formaPagoRepository.findById(solicitud.getFormaPago().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Forma de pago no encontrada"));
                solicitud.setFormaPago(formaPago);
            }

            // Guardar la nueva solicitud
            Solicitud nuevaSolicitud = solicitudRepository.save(solicitud);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El cliente con ID " + clienteId + " no existe.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Map<String, Object>> getSolicitudesByClienteId(@PathVariable Long clienteId) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        Map<String, Object> response = new HashMap<>();

        if (clienteOptional.isPresent()) {
            List<Solicitud> solicitudes = solicitudRepository.findByCliente_Id(clienteId);

            if (solicitudes.isEmpty()) {
                response.put("message", "El cliente con ID " + clienteId + " no tiene solicitudes.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("message", "Solicitudes encontradas para el cliente con ID " + clienteId);
            response.put("solicitudes", solicitudes);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Cliente con ID " + clienteId + " no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> updateSolicitud(@PathVariable Long id, @RequestBody Solicitud solicitudDetails) {
        Optional<Solicitud> updatedSolicitud = solicitudService.updateSolicitud(id, solicitudDetails);
        return updatedSolicitud.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable Long id) {
        if (solicitudService.deleteSolicitud(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}