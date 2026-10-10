package com.peluqueria.pago.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoletaRefDTO {
    private Long idBoleta;
    private String rutCliente;
    private int total;
    private String estadoBoleta;
}
