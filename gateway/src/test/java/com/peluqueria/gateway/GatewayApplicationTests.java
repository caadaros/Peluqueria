package com.peluqueria.gateway;
 
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
 
/*
 * El Gateway no tiene BD, pero sí tiene Eureka Client y el LoadBalancer.
 * Con estas propiedades se deshabilitan para que el contexto levante sin
 * necesitar un servidor Eureka corriendo.
 *
 * WebEnvironment.MOCK: levanta un servlet context simulado (no abre puerto real).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(properties = {
    "eureka.client.enabled=false",
    "eureka.client.register-with-eureka=false",
    "eureka.client.fetch-registry=false",
    "spring.cloud.config.enabled=false"
})
class GatewayApplicationTests {
 
    @Test
    void contextLoads() {
    }
}