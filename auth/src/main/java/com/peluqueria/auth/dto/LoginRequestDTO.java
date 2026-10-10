package com.peluqueria.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Credenciales para iniciar sesión")
public class LoginRequestDTO {

    @Schema(description = "Nombre de usuario", example = "admin")
    @NotBlank(message = "El usuario no puede estar vacío")
    private String username;

    @Schema(description = "Contraseña")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
