package com.peluqueria.boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoBoletaRequestDTO {

    @Schema(description = "Nuevo estado de la boleta", example = "Anulada")
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "Pagada|Anulada", message = "Debe ser Pagada o Anulada")
    private String estadoBoleta;
}
