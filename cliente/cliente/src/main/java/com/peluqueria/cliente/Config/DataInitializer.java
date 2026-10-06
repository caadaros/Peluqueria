package com.peluqueria.cliente.Config;

import com.peluqueria.cliente.Model.Cliente;
import com.peluqueria.cliente.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) {
        if (clienteRepository.count() > 0) {
            log.info(">>> Cliente: BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        clienteRepository.save(new Cliente
            ("111111111", "Lisa", "Simpsons", "123456789", "lisa.simpsons@gmail.com"));
        clienteRepository.save(new Cliente
            ("111111112", "Bart", "Simpsons", "987654321", "bart.simpsons@gmail.com"));
        clienteRepository.save(new Cliente
            ("111111113", "Maggie", "Simpsons", "555555555", "maggie.simpsons@gmail.com"));
        clienteRepository.save(new Cliente
            ("111111114", "Juanita", "Perez", "987654321", "juanita.perez@gmail.com"));
        log.info(">>> Cliente: {} Clientes insertados.", clienteRepository.count());
    }
}
