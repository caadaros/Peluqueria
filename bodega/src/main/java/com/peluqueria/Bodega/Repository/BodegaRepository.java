package com.peluqueria.Bodega.Repository;

import com.peluqueria.Bodega.Model.Bodega;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {
    List<Bodega> findByNombreBodegaContainingIgnoreCase(String nombreBodega);
}
