package com.peluqueria.producto.Repository;

import com.peluqueria.producto.Model.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);
    Optional<Producto> findBySkuProducto(String skuProducto);
}
