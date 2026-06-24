package com.peluqueria.disponibilidadProfesional.Config;

import com.peluqueria.disponibilidadProfesional.Model.DisponibilidadProfesional;
import com.peluqueria.disponibilidadProfesional.Repository.DisponibilidadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{

    private final DisponibilidadRepository disponibilidadRepository;

    @Override
    public void run(String... args) {
        if (disponibilidadRepository.count() > 0) {
            log.info(">>> Disponibilidad: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        disponibilidadRepository.save(new DisponibilidadProfesional
            (null, "222222221", "2026-05-25", "09:00", "12:00"));
        disponibilidadRepository.save(new DisponibilidadProfesional
            (null, "222222222", "2026-05-26", "09:00", "19:00"));
        disponibilidadRepository.save(new DisponibilidadProfesional
            (null, "222222221", "2026-05-26", "10:30", "18:30"));
        disponibilidadRepository.save(new DisponibilidadProfesional
            (null, "222222223", "2026-05-25", "09:00", "19:00"));
        log.info(">>> Disponibilidad: {} Disponibilidades insertadas.", disponibilidadRepository.count());
    }
}