package com.peluqueria.profesional.Repository;
// Repositorio: Es el encargado de hablar con la BD

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.peluqueria.profesional.Model.Profesional;
import java.util.List;

/* TipoServRepository se convierte en una interfáz de la extención de JpaRepository, 
generando automáticamente los CRUD:
.save(entidad): Guarda una nueva fila en la tabla.
.findAll(): Trae todas las filas de la tabla.
.findById(id): Busca una fila específica por su ID.
.deleteById(ID id): Borra esa fila específica.
.deleteAll(): Borra todos los registros de la tabla. 

En caso que necesitemos una búsqueda por otros parámetros se deben establecer dentro 
de la misma extensión
*/
@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, String>{
    // Los métodos CRUD heredados son suficientes para este microservicio.  
    List<Profesional> findByEspecialidad(String especialidad);
    List<Profesional> findByEstado(String estado);
}
