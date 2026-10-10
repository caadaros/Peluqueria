package com.peluqueria.registro.Repository;

import com.peluqueria.registro.Model.Registro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
    List<Registro> findByRutCliente(String rutCliente);
    List<Registro> findByRutProfesional(String rutProfesional);
}
