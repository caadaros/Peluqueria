package com.peluqueria.registro.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClientCliente(WebClient.Builder builder) {
        return builder.baseUrl("http://cliente").build();
    }

    @Bean
    public WebClient webClientProfesional(WebClient.Builder builder) {
        return builder.baseUrl("http://profesional").build();
    }

    @Bean
    public WebClient webClientTipoServicio(WebClient.Builder builder) {
        return builder.baseUrl("http://tipoServicio").build();
    }

    @Bean
    public WebClient webClientAgenda(WebClient.Builder builder) {
        return builder.baseUrl("http://agenda").build();
    }

    @Bean
    public WebClient webClientProducto(WebClient.Builder builder) {
        return builder.baseUrl("http://producto").build();
    }
}
