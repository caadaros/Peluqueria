package com.peluqueria.profesional.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesionalResponseDTO {
    private String rutProfesional;
    private String nombreProfesional;
    private String apellidoProfesional;
    private int telefonoProfesional;
    private String correoProfesional;
    private String especialidad;
    private String estado;

}
