package com.peluqueria.boleta.Repository;

import com.peluqueria.boleta.Model.Boleta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByRutCliente(String rutCliente);
}
