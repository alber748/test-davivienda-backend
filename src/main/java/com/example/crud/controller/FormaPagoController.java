package com.example.crud.controller;

import com.example.crud.model.FormaPago;
import com.example.crud.repository.FormaPagoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/formas-pago")
public class FormaPagoController {

    private final FormaPagoRepository formaPagoRepository;

    public FormaPagoController(FormaPagoRepository formaPagoRepository) {
        this.formaPagoRepository = formaPagoRepository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<FormaPago> getAllFormasPago() {
        return formaPagoRepository.findAll();
    }
}