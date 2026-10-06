package com.peluqueria.disponibilidadProfesional.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadResponseDTO {
    private Long idDisponibilidad;
    private String rutProfesional;
    private String fecha;
    private String horaInicio;
    private String horaFin;
}
