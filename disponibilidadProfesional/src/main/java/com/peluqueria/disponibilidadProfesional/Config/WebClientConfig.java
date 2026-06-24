package com.peluqueria.disponibilidadProfesional.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.profesional.url}")
    private String profesionalUrl;

    @Bean
    public WebClient webClient() {

        return WebClient.builder()
                .baseUrl(profesionalUrl) // Ahora esto valdrá "http://localhost:8082"
                .build();
    }
}
