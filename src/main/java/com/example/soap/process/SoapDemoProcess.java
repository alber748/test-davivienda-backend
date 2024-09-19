package com.example.soap.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.tempuri.FindPerson;
import org.tempuri.FindPersonResponse;

@Component
public class SoapDemoProcess {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public FindPersonResponse callSoapService(int id) {
        FindPerson request = new FindPerson();
        request.setId("" + id);

        // Registrar la solicitud
        System.out.println("Enviando solicitud SOAP con ID: " + id);

        try {
            // Hacer la llamada al servicio SOAP
            FindPersonResponse response = (FindPersonResponse) webServiceTemplate.marshalSendAndReceive(request);

            // Registrar la respuesta
            System.out.println("Respuesta recibida del servicio SOAP: " + response);

            // Validar si la respuesta es nula
            if (response == null || response.getFindPersonResult() == null) {
                System.out.println("La respuesta SOAP es nula o no contiene resultados.");
                return new FindPersonResponse(); // Retornar objeto vacío para manejar el caso de null
            }

            return response;
        } catch (Exception e) {
            // Registrar el error
            System.err.println("Error durante la invocación del servicio SOAP: " + e.getMessage());
            e.printStackTrace();
            return new FindPersonResponse(); // Retornar objeto vacío en caso de error
        }
    }
}