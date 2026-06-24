package com.peluqueria.disponibilidadProfesional.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disponibilidad_profesional")
public class DisponibilidadProfesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisponibilidad;

    @Column(name = "rutProfesional", nullable = false)
    private String rutProfesional;

    @Column(nullable = false, columnDefinition = "DATE")
    private String fecha;

    @Column(nullable = false, columnDefinition = "TIME")
    private String horaInicio;

    @Column(nullable = false, columnDefinition = "TIME")
    private String horaFin;
}
