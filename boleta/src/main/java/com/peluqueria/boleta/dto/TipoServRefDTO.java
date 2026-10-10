package com.peluqueria.boleta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoServRefDTO {
    private Long idTipoServicio;
    private String descripcionServicio;
    private int precioServicio;
}
