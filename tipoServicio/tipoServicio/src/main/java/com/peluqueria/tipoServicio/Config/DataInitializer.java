package com.peluqueria.tipoServicio.Config;

import com.peluqueria.tipoServicio.Model.TipoServicio;
import com.peluqueria.tipoServicio.Repository.TipoServRepository;
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

    private final TipoServRepository tipoRepository;

    @Override
    public void run(String... args) {
        if (tipoRepository.count() > 0) {
            log.info(">>> Tipo Servicio: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        tipoRepository.save(new TipoServicio(null, "Alisado permanente", 50000, 2, "Peluqueria"));
        tipoRepository.save(new TipoServicio(null, "Corte de cabello",10000, 1, "Peluqueria"));
        tipoRepository.save(new TipoServicio(null, "Baño de Color",80000, 3, "Peluqueria"));
        tipoRepository.save(new TipoServicio(null, "Uñas acrílicas",30000, 1, "Mano"));
        log.info(">>> Tipo Servicio: {} Tipo de Servicios insertados.", tipoRepository.count());
    }
}
