package com.example.crud.controller;

import com.example.crud.model.ActividadesEconomicas;
import com.example.crud.model.Cliente;
import com.example.crud.model.EstadoCivil;
import com.example.crud.repository.ActividadesEconomicasRepository;
import com.example.crud.repository.ClienteRepository;
import com.example.crud.repository.EstadoCivilRepository;
import com.example.crud.repository.SolicitudRepository;
import com.example.crud.service.ClienteService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private ActividadesEconomicasRepository actividadesEconomicasRepository;
    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {

        System.out.println(cliente);
        // Validar y asignar los objetos relacionados
        if (cliente.getActividadesEconomicas() != null) {
            ActividadesEconomicas actividad = actividadesEconomicasRepository.findById(cliente.getActividadesEconomicas().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Actividad económica no encontrada"));
            cliente.setActividadesEconomicas(actividad);
        }

        if (cliente.getEstadoCivil() != null) {
            EstadoCivil estadoCivil = estadoCivilRepository.findById(cliente.getEstadoCivil().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Estado civil no encontrado"));
            cliente.setEstadoCivil(estadoCivil);
        }

        return clienteRepository.save(cliente);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            // Asigna las propiedades del cliente actualizadas
            cliente.setNombres(clienteDetails.getNombres());
            cliente.setApellidos(clienteDetails.getApellidos());
            cliente.setDui(clienteDetails.getDui());
            cliente.setNit(clienteDetails.getNit());
            cliente.setSexo(clienteDetails.getSexo());

            if (cliente.getActividadesEconomicas() != null) {
                Hibernate.initialize(cliente.getActividadesEconomicas());
            }
            if (cliente.getEstadoCivil() != null) {
                Hibernate.initialize(cliente.getEstadoCivil());
            }

            // Validar y asignar las relaciones, igual que en createCliente
            if (clienteDetails.getActividadesEconomicas() != null) {
                ActividadesEconomicas actividad = actividadesEconomicasRepository.findById(clienteDetails.getActividadesEconomicas().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Actividad económica no encontrada"));
                cliente.setActividadesEconomicas(actividad);
            }

            if (clienteDetails.getEstadoCivil() != null) {
                EstadoCivil estadoCivil = estadoCivilRepository.findById(clienteDetails.getEstadoCivil().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Estado civil no encontrado"));
                cliente.setEstadoCivil(estadoCivil);
            }

            Cliente updatedCliente = clienteRepository.save(cliente);
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok("Cliente con ID " + id + " ha sido eliminado correctamente, junto con sus solicitudes asociadas.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con ID " + id + " no fue encontrado.");
        }
    }
}