package com.peluqueria.agenda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.peluqueria.agenda.Model.Agenda;
 
import java.util.List;
 
@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
 
    List<Agenda> findByRutProfesional(String rutProfesional);
 
    List<Agenda> findByRutCliente(String rutCliente);
 
    List<Agenda> findByFecha(String fecha);
 
    // Una cita cancelada NO ocupa el horario
    @Query("SELECT COUNT(a) > 0 FROM Agenda a " +
           "WHERE a.rutProfesional = :rut AND a.fecha = :fecha " +
           "AND a.horaInicio = :hora " +
           "AND LOWER(a.estadoCita) NOT LIKE 'cancel%'")
    boolean existeCitaActiva(@Param("rut") String rut,
                             @Param("fecha") String fecha,
                             @Param("hora") String hora);
}
