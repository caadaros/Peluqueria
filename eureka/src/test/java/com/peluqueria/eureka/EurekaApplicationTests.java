package com.peluqueria.eureka;
 
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
 
/*
 * WebEnvironment.NONE: no levanta ningún servidor web.
 * Esto evita que Eureka Server intente abrir puertos y conectarse a sí mismo durante el test.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EurekaApplicationTests {
 
    @Test
    void contextLoads() {
    }
}