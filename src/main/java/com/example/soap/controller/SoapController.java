package com.example.soap.controller;

import com.example.soap.process.SoapDemoProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.FindPersonResponse;

@RestController
public class SoapController {

    @Autowired
    private SoapDemoProcess soapDemoProcess;

    @CrossOrigin(origins = "*")
    @GetMapping("/consultarInfoPersona")
    public ResponseEntity<FindPersonResponse> getPersonInfo(@RequestParam int id) {
        System.out.println("Solicitud recibida para ID: " + id);
        FindPersonResponse response = soapDemoProcess.callSoapService(id);
        return ResponseEntity.ok(response);
    }
}