package com.peluqueria.disponibilidadProfesional.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * Builder con @LoadBalanced: permite que Spring Cloud resuelva
     * "http://NOMBRE-SERVICIO" consultando el registro de Eureka.
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    // Nombre exacto: spring.application.name=profesional
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://profesional").build();
    }
}