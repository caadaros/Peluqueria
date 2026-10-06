package com.peluqueria.disponibilidadProfesional.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.peluqueria.disponibilidadProfesional.Model.DisponibilidadProfesional;
import java.util.List;

public interface DisponibilidadRepository extends JpaRepository<DisponibilidadProfesional, Long> {

    List<DisponibilidadProfesional> findByRutProfesional(String rutProfesional);
    List<DisponibilidadProfesional> findByFecha(String fecha);
    List<DisponibilidadProfesional> findByRutProfesionalAndFechaAndHoraInicio(String rutProfesional, String fecha, String horaInicio);
    List<DisponibilidadProfesional> findByRutProfesionalAndFecha(String rutProfesional, String fecha);
}