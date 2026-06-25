package com.peluqueria.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de un cliente")
public class ClienteRequestDTO {

    @Schema(description = "RUT del cliente", example = "12345678-9")   
    @NotBlank(message = "El rut no puede estar vacío")
    private String rutCliente;

    @Schema(description = "Nombre del cliente", example = "Juan")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreCliente;

    @Schema(description = "Apellido del cliente", example = "Pérez")
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidoCliente;

    @Schema(description = "Teléfono del cliente", example = "987654321")
    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefonoCliente;

    @Email
    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@ejemplo.com")
    @NotBlank(message = "El correo no puede estar vacío")
    private String correoCliente;
}
