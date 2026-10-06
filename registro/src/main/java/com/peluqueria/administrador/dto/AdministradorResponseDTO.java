package com.peluqueria.administrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorResponseDTO {
    private String rutAdministrador;
    private String nombreAdministrador;
    private String apellidoAdministrador;
    private String telefonoAdministrador;
    private String correoAdministrador;
    private String passwordAdministrador;
    private String estado;

}
