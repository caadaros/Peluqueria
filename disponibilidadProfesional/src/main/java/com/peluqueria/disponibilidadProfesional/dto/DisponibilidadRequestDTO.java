package com.peluqueria.disponibilidadProfesional.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadRequestDTO {

    @NotBlank(message = "El rut no puede estar vacío")
    private String rutProfesional;

    @NotBlank(message = "Debe ingresas fecha de disponibilidad")  
    private String fecha;

    @NotBlank(message = "Debe ingresar hora de inicio")  
    private String horaInicio;

    @NotBlank(message = "Debe ingresar hora de término") 
    private String horaFin;

}