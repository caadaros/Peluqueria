package com.peluqueria.registro.Config;

import com.peluqueria.registro.Model.Registro;
import com.peluqueria.registro.Repository.RegistroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RegistroRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            log.info(">>> Registro: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        repository.save(new Registro(null, 1, "111111111", "222222221", 2L, 1L, 1, "2026-05-25", "Atención de ejemplo"));
        log.info(">>> Registro: {} registros insertados.", repository.count());
    }
}
