package com.peluqueria.producto.Config;

import com.peluqueria.producto.Model.Producto;
import com.peluqueria.producto.Repository.ProductoRepository;
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

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {
        if (productoRepository.count() > 0) {
            log.info(">>> Producto: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        productoRepository.save(new Producto(null, "Shampoo Anticaspa Garnier 500 ml", 50000, "Unidad"));
        productoRepository.save(new Producto(null, "Acondicionador Sedal",10000, "Unidad"));
        productoRepository.save(new Producto(null, "Tintura de Pelo Cobrizo Color",80000, "Unidad"));
        productoRepository.save(new Producto(null, "Aceite de coco Natural",30000, "Unidad"));
        log.info(">>> Producto: {} Productos insertados.", productoRepository.count());
    }
}
