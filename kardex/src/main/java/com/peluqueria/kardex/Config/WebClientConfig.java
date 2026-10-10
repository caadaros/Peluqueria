package com.peluqueria.kardex.Config;

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
    public WebClient webClientProducto(WebClient.Builder builder) {
        return builder.baseUrl("http://producto").build();
    }

    @Bean
    public WebClient webClientBodega(WebClient.Builder builder) {
        return builder.baseUrl("http://bodega").build();
    }
}
