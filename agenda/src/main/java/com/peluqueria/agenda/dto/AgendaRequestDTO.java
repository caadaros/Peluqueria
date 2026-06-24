package com.peluqueria.agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaRequestDTO {

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotBlank(message = "La hora es obligatoria")
    private String horaInicio;

    @NotNull(message = "El servicio es obligatorio")
    private Long idTipoServicio;

    @NotBlank(message = "El profesional es obligatorio")
    private String rutProfesional;

    @NotBlank(message = "El cliente es obligatorio")
    private String rutCliente;

    @NotBlank(message = "El estado es obligatorio")
    private String estadoCita;
}