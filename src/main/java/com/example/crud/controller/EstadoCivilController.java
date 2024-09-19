package com.example.crud.controller;

import com.example.crud.model.EstadoCivil;
import com.example.crud.repository.EstadoCivilRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estados-civiles")
public class EstadoCivilController {

    private final EstadoCivilRepository estadoCivilRepository;

    public EstadoCivilController(EstadoCivilRepository estadoCivilRepository) {
        this.estadoCivilRepository = estadoCivilRepository;
    }
    @CrossOrigin(origins = "*")
    @GetMapping
    public List<EstadoCivil> getAllEstadosCiviles() {
        return estadoCivilRepository.findAll();
    }
}