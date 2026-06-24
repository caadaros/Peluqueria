package com.peluqueria.profesional.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesionalRequestDTO {
    @NotBlank(message = "El rut no puede estar vacío")
    private String rutProfesional;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreProfesional;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidoProfesional;
    
    @NotNull(message = "El teléfono no puede estar vacío")
    @Positive(message = "Favor ingresar número válido")
    private int telefonoProfesional;

    @Email
    @NotBlank(message = "El correo no puede estar vacío")
    private String correoProfesional;

    private String especialidad;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
