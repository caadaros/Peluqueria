package com.peluqueria.boleta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRefDTO {
    private Long idProducto;
    private String nombreProducto;
    private int precioProducto;
}
