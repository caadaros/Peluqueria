package com.peluqueria.bodega.Config;

import com.peluqueria.bodega.Model.Bodega;
import com.peluqueria.bodega.Repository.BodegaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BodegaRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            log.info(">>> Bodega: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        repository.save(new Bodega(null, "Bodega central", "Av. Principal 123", "Activa"));
        repository.save(new Bodega(null, "Sucursal Plaza Norte", "Av. Américo Vespucio 1737", "Activa"));
        log.info(">>> Bodega: {} registros insertados.", repository.count());
    }
}
