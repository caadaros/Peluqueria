package com.peluqueria.producto.Config;

import com.peluqueria.producto.Model.Producto;
import com.peluqueria.producto.Repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            log.info(">>> Producto: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        repository.save(new Producto(null, "SHAMP-001", "Shampoo profesional", "500 ml", 8500, "Activo"));
        repository.save(new Producto(null, "TINTE-001", "Tinte castaño", "Tubo 60 ml", 6500, "Activo"));
        repository.save(new Producto(null, "ACOND-001", "Acondicionador", "500 ml", 9000, "Activo"));
        log.info(">>> Producto: {} registros insertados.", repository.count());
    }
}
