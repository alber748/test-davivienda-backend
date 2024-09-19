package com.example.crud.service;

import com.example.crud.model.Cliente;
import com.example.crud.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> updateCliente(Long id, Cliente clienteDetails) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setNombres(clienteDetails.getNombres());
            cliente.setApellidos(clienteDetails.getApellidos());
            cliente.setDui(clienteDetails.getDui());
            cliente.setSexo(clienteDetails.getSexo());
            cliente.setNit(clienteDetails.getNit());
            cliente.setEstadoCivil(clienteDetails.getEstadoCivil());
            cliente.setActividadesEconomicas(clienteDetails.getActividadesEconomicas());
            cliente.setSolicitudes(clienteDetails.getSolicitudes());
            return Optional.of(clienteRepository.save(cliente));
        }
        return Optional.empty();
    }

    public boolean deleteCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}