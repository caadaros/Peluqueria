package com.peluqueria.bodega.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodegaResponseDTO {
    private Long idBodega;
    private String nombreBodega;
    private String direccionBodega;
    private String estado;
}
