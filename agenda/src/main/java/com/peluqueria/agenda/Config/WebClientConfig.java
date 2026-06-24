package com.peluqueria.agenda.Config;

import org.springframework.beans.factory.annotation.Value; // <--- Asegúrate de importar esto
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // CORRECCIÓN: Agrega esta anotación para amarrar la variable con tu archivo properties
    @Value("${ms.disponibilidad.url}")
    private String disponibilidadUrl;

    @Value("${ms.tiposervicio.url}")
    private String tipoServicioUrl;

    @Value("${ms.cliente.url}")
    private String clienteUrl;

    @Bean
    public WebClient webClientDisponibilidad() {
        return WebClient.builder()
                .baseUrl(disponibilidadUrl) // Ahora esto valdrá "http://localhost:8083"
                .build();
    }

    @Bean
    public WebClient webClientTipoServicio() {
        return WebClient.builder()
                .baseUrl(tipoServicioUrl) // Ahora esto valdrá "http://localhost:8081"
                .build();
    }

    @Bean
    public WebClient webClientCliente() {
        return WebClient.builder()
                .baseUrl(clienteUrl) // Ahora esto valdrá "http://localhost:8084"
                .build();
    }
}