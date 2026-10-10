package com.peluqueria.kardex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDTO {
    private Long idProducto;
    private Long idBodega;
    private long stock;
}
