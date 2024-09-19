package com.example.soap.config;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.client.support.interceptor.ClientInterceptorAdapter;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;

import java.io.IOException;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.tempuri");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        webServiceTemplate.setDefaultUri("https://www.crcind.com:443/csp/samples/SOAP.Demo.cls");

        // Agregamos el interceptor de logging
        ClientInterceptor[] interceptors = new ClientInterceptor[]{new SoapLoggingInterceptor()};
        webServiceTemplate.setInterceptors(interceptors);

        return webServiceTemplate;
    }

    @Bean
    public HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();

        CloseableHttpClient httpClient = HttpClients.custom()
                .disableAutomaticRetries()
                .addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
                    request.removeHeaders("Content-Length");
                })
                .build();

        messageSender.setHttpClient(httpClient);
        return messageSender;
    }

    // Interceptor personalizado para logging de las solicitudes y respuestas SOAP
    public class SoapLoggingInterceptor extends ClientInterceptorAdapter {
        @Override
        public boolean handleRequest(MessageContext messageContext) {
            WebServiceMessage request = messageContext.getRequest();
            System.out.println("SOAP Request:");
            try {
                request.writeTo(System.out);  // Imprime la solicitud SOAP en la consola
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public boolean handleResponse(MessageContext messageContext) {
            WebServiceMessage response = messageContext.getResponse();
            System.out.println("SOAP Response:");
            try {
                response.writeTo(System.out);  // Imprime la respuesta SOAP en la consola
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public boolean handleFault(MessageContext messageContext) {
            WebServiceMessage fault = messageContext.getResponse();
            System.out.println("SOAP Fault:");
            try {
                fault.writeTo(System.out);  // Imprime el fallo SOAP en la consola
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}