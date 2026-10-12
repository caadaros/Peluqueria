package com.peluqueria.tipoServicio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;MODE=Oracle;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS PELUQUERIA",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "eureka.client.enabled=false",
    "app.jwt.secret=VGVzdFNlY3JldFBhcmFQcnVlYmFzVW5pdGFyaWFzMTIzNDU2Nzg5MDEyMzQ1Njc4"
})
class TipoServicioApplicationTests {

    @Test
    void contextLoads() {
    }
}
