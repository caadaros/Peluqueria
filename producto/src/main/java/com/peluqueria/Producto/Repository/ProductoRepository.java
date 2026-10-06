package com.peluqueria.producto.Repository;
// Repositorio: Es el encargado de hablar con la BD

import com.peluqueria.producto.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/* ProductoRepository se convierte en una interfáz de la extención de JpaRepository, 
generando automáticamente los CRUD:
.save(entidad): Guarda una nueva fila en la tabla.
.findAll(): Trae todas las filas de la tabla.
.findById(id): Busca una fila específica por su ID.
.deleteById(ID id): Borra esa fila específica.
.deleteAll(): Borra todos los registros de la tabla. 

En caso que necesitemos una búsqueda por otros parámetros se deben establecer dentro 
de la misma extensión
*/

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Los métodos CRUD heredados son suficientes para este microservicio.

}


