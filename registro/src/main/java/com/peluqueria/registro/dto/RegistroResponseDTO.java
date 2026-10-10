package com.peluqueria.registro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroResponseDTO {
    private Long idRegistro;
    private Integer idAgenda;
    private String rutCliente;
    private String rutProfesional;
    private Long idTipoServicio;
    private Long idProducto;
    private Integer cantidadProducto;
    private String fecha;
    private String observaciones;
}
