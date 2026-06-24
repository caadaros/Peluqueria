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

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(disponibilidadUrl) // Ahora esto valdrá "http://localhost:8082"
                .build();
    }
}