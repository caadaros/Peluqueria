package com.peluqueria.cliente.dto;

// Lo que devolveré, por lo que debería devolver toda la información de la clase
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    private String rutCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String correoCliente;
}
