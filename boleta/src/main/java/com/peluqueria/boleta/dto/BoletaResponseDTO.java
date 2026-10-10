package com.peluqueria.boleta.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoletaResponseDTO {
    private Long idBoleta;
    private LocalDateTime fechaEmision;
    private String rutCliente;
    private String rutProfesional;
    private Integer idAgenda;
    private int total;
    private String estadoBoleta;
    private List<DetalleResponseDTO> detalles;
}
