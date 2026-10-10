package com.peluqueria.pago.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {
    private Long idPago;
    private Long idBoleta;
    private String rutCliente;
    private int monto;
    private String metodoPago;
    private String estadoPago;
    private LocalDateTime fechaPago;
}
