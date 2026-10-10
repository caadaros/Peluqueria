package com.peluqueria.auth.Config;

import com.peluqueria.auth.Model.Usuario;
import com.peluqueria.auth.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Requisito: crear el primer ADMIN desde variables de entorno cuando no existen usuarios.
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @Value("${app.bootstrap.admin-username}")
    private String adminUsername;

    @Value("${app.bootstrap.admin-password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            log.info(">>> Auth: BD ya tiene usuarios, se omite la carga inicial.");
            return;
        }
        if (adminUsername.isBlank() || adminPassword.isBlank()) {
            log.warn(">>> Auth: faltan ADMIN_USERNAME / ADMIN_PASSWORD, no se creó el administrador inicial.");
            return;
        }
        repository.save(new Usuario(null, adminUsername, encoder.encode(adminPassword), "ADMIN", true));
        log.info(">>> Auth: administrador inicial '{}' creado.", adminUsername);
    }
}
