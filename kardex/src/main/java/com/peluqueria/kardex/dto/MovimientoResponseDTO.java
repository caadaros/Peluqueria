package com.peluqueria.kardex.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoResponseDTO {
    private Long idMovimiento;
    private Long idProducto;
    private Long idBodega;
    private String tipoMovimiento;
    private int cantidad;
    private String motivo;
    private LocalDateTime fechaMovimiento;
    private String registradoPor;
}
