package com.peluqueria.cliente.dto;

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
public class ClienteRequestDTO {
    @NotBlank(message = "El rut no puede estar vacío")
    private String rutCliente;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreCliente;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidoCliente;
    
    @NotNull(message = "El teléfono no puede estar vacío")
    @Positive(message = "Favor ingresar número válido")
    private int telefonoCliente;

    @Email
    @NotBlank(message = "El correo no puede estar vacío")
    private String correoCliente;
}
