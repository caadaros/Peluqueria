package com.peluqueria.administrador.Config;

import com.peluqueria.administrador.Model.Administrador;
import com.peluqueria.administrador.Repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
    private final AdministradorRepository administradorRepository;

    @Override
    public void run(String... args) {
        if (administradorRepository.count() > 0) {
            log.info(">>> Tipo Servicio: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        administradorRepository.save(new Administrador
            ("333333331", "Marcelo", "Garcia", "321654987", "marcelo.garcia@gmail.com", "123MGarcia", "Activo"));
        administradorRepository.save(new Administrador
            ("333333332", "Marcela", "Gatica", "987654321", "marcela.gatica@gmail.com","123MGatica", "Activo"));
        administradorRepository.save(new Administrador
            ("333333333", "Luis", "Mena", "987654321", "luis.mena@gmail.com",  "123MLuis", "Activo"));
        administradorRepository.save(new Administrador
            ("333333334", "Luisa", "Manson", "987654321", "luisa.manson@gmail.com", "123MLuisa", "Inactivo"));
        log.info(">>> Administrador: {} Administradores insertados.", administradorRepository.count());
    }
}
