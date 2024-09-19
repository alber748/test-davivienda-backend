package com.example.crud.service;

import com.example.crud.model.Solicitud;
import com.example.crud.model.FormaPago;
import com.example.crud.repository.SolicitudRepository;
import com.example.crud.repository.FormaPagoRepository;
import com.example.crud.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FormaPagoRepository formaPagoRepository;

    public List<Solicitud> getAllSolicitudes() {
        return solicitudRepository.findAll();
    }

    public Optional<Solicitud> getSolicitudById(Long id) {
        return solicitudRepository.findById(id);
    }

    public Solicitud createSolicitud(Solicitud solicitud) {
        // Verificar y asignar la relación con cliente
        if (solicitud.getClienteId() != null) {
            solicitud.setCliente(
                    clienteRepository.findById(solicitud.getClienteId())
                            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"))
            );
        }

        // Verificar y asignar la relación con forma de pago
        if (solicitud.getFormaPago() != null && solicitud.getFormaPago().getId() != null) {
            FormaPago formaPago = formaPagoRepository.findById(solicitud.getFormaPago().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Forma de pago no encontrada"));
            solicitud.setFormaPago(formaPago);
        }

        return solicitudRepository.save(solicitud);
    }

    public Optional<Solicitud> updateSolicitud(Long id, Solicitud solicitudDetails) {
        Optional<Solicitud> solicitudOptional = solicitudRepository.findById(id);

        if (solicitudOptional.isPresent()) {
            Solicitud solicitud = solicitudOptional.get();

            solicitud.setFechaCreacion(solicitudDetails.getFechaCreacion());
            solicitud.setMonto(solicitudDetails.getMonto());
            solicitud.setPlazo(solicitudDetails.getPlazo());

            // Actualizar cliente si es necesario
            if (solicitudDetails.getClienteId() != null) {
                solicitud.setCliente(
                        clienteRepository.findById(solicitudDetails.getClienteId())
                                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"))
                );
            }

            // Actualizar forma de pago si es necesario
            if (solicitudDetails.getFormaPago() != null && solicitudDetails.getFormaPago().getId() != null) {
                FormaPago formaPago = formaPagoRepository.findById(solicitudDetails.getFormaPago().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Forma de pago no encontrada"));
                solicitud.setFormaPago(formaPago);
            }

            return Optional.of(solicitudRepository.save(solicitud));
        }
        return Optional.empty();
    }

    public boolean deleteSolicitud(Long id) {
        if (solicitudRepository.existsById(id)) {
            solicitudRepository.deleteById(id);
            return true;
        }
        return false;
    }
}