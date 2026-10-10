package com.peluqueria.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long idProducto;
    private String skuProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private int precioProducto;
    private String estado;
}
