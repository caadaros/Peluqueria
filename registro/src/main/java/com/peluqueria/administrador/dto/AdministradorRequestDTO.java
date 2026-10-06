package com.peluqueria.administrador.dto;

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
@Schema(description = "DTO para la solicitud de creación o actualización de un administrador")
public class AdministradorRequestDTO {

    @Schema(description = "RUT del administrador", example = "12345678-9")
    @NotBlank(message = "El rut no puede estar vacío")
    private String rutAdministrador;

    @Schema(description = "Nombre del administrador", example = "Juan")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreAdministrador;

    @Schema(description = "Apellido del administrador", example = "Pérez")
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidoAdministrador;

    @Schema(description = "Teléfono del administrador", example = "987654321")
    @NotNull(message = "El teléfono no puede estar vacío")
    @Positive(message = "Favor ingresar número válido")
    private String telefonoAdministrador;

    @Schema(description = "Correo electrónico del administrador", example = "juan.perez@ejemplo.com")
    @Email
    @NotBlank(message = "El correo no puede estar vacío")
    private String correoAdministrador;

    @Schema(description = "Contraseña del administrador", example = "password123")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String passwordAdministrador;

    @Schema(description = "Estado del administrador", example = "Activo")
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
