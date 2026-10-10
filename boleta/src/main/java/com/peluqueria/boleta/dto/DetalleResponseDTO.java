package com.peluqueria.boleta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleResponseDTO {
    private String tipoItem;
    private Long idItem;
    private String descripcion;
    private int cantidad;
    private int precioUnitario;
    private int subtotal;
}
