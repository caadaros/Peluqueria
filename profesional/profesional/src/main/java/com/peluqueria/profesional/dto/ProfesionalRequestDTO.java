package com.peluqueria.profesional.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de un profesional")
public class ProfesionalRequestDTO {

    @Schema(description = "RUT del profesional", example = "12345678-9")
    @NotBlank(message = "El rut no puede estar vacío")
    private String rutProfesional;

    @Schema(description = "Nombre del profesional", example = "Juan")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreProfesional;

    @Schema(description = "Apellido del profesional", example = "Pérez")
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidoProfesional;

    @Schema(description = "Teléfono del profesional", example = "987654321")
    @NotNull(message = "El teléfono no puede estar vacío")
    @Positive(message = "Favor ingresar número válido")
    private String telefonoProfesional;

    @Schema(description = "Correo electrónico del profesional", example = "juan.perez@ejemplo.com")
    @Email
    @NotBlank(message = "El correo no puede estar vacío")
    private String correoProfesional;

    @Schema(description = "Especialidad del profesional", example = "Peluquería")
    private String especialidad;

    @Schema(description = "Estado del profesional", example = "Activo")
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
