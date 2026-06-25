package com.peluqueria.disponibilidadProfesional.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de una disponibilidad")
public class DisponibilidadRequestDTO {

    @NotBlank(message = "El rut no puede estar vacío")
    @Schema(description = "RUT del profesional", example = "12345678-9")
    private String rutProfesional;

    @NotBlank(message = "Debe ingresas fecha de disponibilidad")  
    @Schema(description = "Fecha de la disponibilidad", example = "2023-10-10")
    private String fecha;

    @NotBlank(message = "Debe ingresar hora de inicio")  
    @Schema(description = "Hora de inicio de la disponibilidad", example = "09:00")
    private String horaInicio;

    @NotBlank(message = "Debe ingresar hora de término") 
    @Schema(description = "Hora de término de la disponibilidad", example = "17:00")
    private String horaFin;

}