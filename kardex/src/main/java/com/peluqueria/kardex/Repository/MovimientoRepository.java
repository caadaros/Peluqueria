package com.peluqueria.kardex.Repository;

import com.peluqueria.kardex.Model.Movimiento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByIdProducto(Long idProducto);
    List<Movimiento> findByIdBodega(Long idBodega);
    List<Movimiento> findByIdProductoAndIdBodega(Long idProducto, Long idBodega);
}
