package com.peluqueria.bodega.Config;

import com.peluqueria.bodega.Model.Bodega;
import com.peluqueria.bodega.Repository.BodegaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// ═══════════════════════════════════════════════════
// solo inserta si la
// BD está vacía (count == 0).
// ═══════════════════════════════════════════════════

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BodegaRepository bodegaRepository;

    @Override
    public void run(String... args) {
        if (bodegaRepository.count() > 0) {
            log.info(">>> Bodega: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        bodegaRepository.save(new Bodega(null, "Bodega Principal", "Calle 123", "Santiago"));
        bodegaRepository.save(new Bodega(null, "Bodega Secundaria", "Avenida XYZ", "Valparaiso"));
        log.info(">>> Bodega: {} Bodegas insertadas.", bodegaRepository.count());
    }
}
