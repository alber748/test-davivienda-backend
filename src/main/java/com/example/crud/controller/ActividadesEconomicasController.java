package com.example.crud.controller;

import com.example.crud.model.ActividadesEconomicas;
import com.example.crud.repository.ActividadesEconomicasRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actividades-economicas")
public class ActividadesEconomicasController {

    private final ActividadesEconomicasRepository actividadesEconomicasRepository;

    public ActividadesEconomicasController(ActividadesEconomicasRepository actividadesEconomicasRepository) {
        this.actividadesEconomicasRepository = actividadesEconomicasRepository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<ActividadesEconomicas> getAllActividadesEconomicas() {
        return actividadesEconomicasRepository.findAll();
    }
}