package com.peluqueria.kardex.Config;

import com.peluqueria.kardex.Model.Movimiento;
import com.peluqueria.kardex.Repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MovimientoRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            log.info(">>> Kardex: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        repository.save(new Movimiento(null, 1L, 1L, "ENTRADA", 50, "Stock inicial", java.time.LocalDateTime.now(), "sistema"));
        log.info(">>> Kardex: {} registros insertados.", repository.count());
    }
}
