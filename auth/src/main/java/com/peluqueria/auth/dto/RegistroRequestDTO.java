package com.peluqueria.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar un usuario")
public class RegistroRequestDTO {

    @Schema(description = "Nombre de usuario", example = "recepcion1")
    @NotBlank(message = "El usuario no puede estar vacío")
    @Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres")
    private String username;

    @Schema(description = "Contraseña (mínimo 8 caracteres)")
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
    private String password;

    @Schema(description = "Rol del usuario", example = "RECEPCION")
    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|RECEPCION|PROFESIONAL", message = "Rol inválido")
    private String rol;
}
