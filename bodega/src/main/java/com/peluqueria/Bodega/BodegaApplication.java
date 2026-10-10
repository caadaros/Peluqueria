package com.peluqueria.bodega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BodegaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BodegaApplication.class, args);
    }
}
