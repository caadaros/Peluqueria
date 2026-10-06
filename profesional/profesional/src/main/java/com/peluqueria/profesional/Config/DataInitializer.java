package com.peluqueria.profesional.Config;

import com.peluqueria.profesional.Model.Profesional;
import com.peluqueria.profesional.Repository.ProfesionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
    private final ProfesionalRepository profesionalRepository;

    @Override
    public void run(String... args) {
        if (profesionalRepository.count() > 0) {
            log.info(">>> Tipo Servicio: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        profesionalRepository.save(new Profesional
            ("222222221", "Marge", "Simpsons", "321654987", "marge.simpsons@gmail.com", "Colorista", "Activo"));
        profesionalRepository.save(new Profesional
            ("222222222", "Homero", "Simpsons", "987654321", "homero.simpsons@gmail.com","Peluqueria", "Activo"));
        profesionalRepository.save(new Profesional
            ("222222223", "Lenny", "Leonard", "987654321", "lenny.leonard@gmail.com",  "Peluqueria", "Activo"));
        profesionalRepository.save(new Profesional
            ("222222224", "Carl", "Carlson", "987654321", "carl.carlson@gmail.com", "Colorista", "Inactivo"));
        log.info(">>> Profesional: {} Profesionales insertados.", profesionalRepository.count());
    }
}
