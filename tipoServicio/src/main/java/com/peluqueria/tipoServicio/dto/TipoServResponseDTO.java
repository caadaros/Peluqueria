package com.peluqueria.tipoServicio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoServResponseDTO {
    private Long idTipoServicio;
    private String descripcionServicio;
    private int precioServicio; 
    private float duracionHoras;
    private String especialidadAsociada;
}
