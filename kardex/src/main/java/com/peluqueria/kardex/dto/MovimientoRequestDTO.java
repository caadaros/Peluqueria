package com.peluqueria.kardex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de movimiento")
public class MovimientoRequestDTO {

    @Schema(description = "ID del producto", example = "1")
    @NotNull(message = "El producto es obligatorio")
    private Long idProducto;

    @Schema(description = "ID de la bodega", example = "1")
    @NotNull(message = "La bodega es obligatoria")
    private Long idBodega;

    @Schema(description = "Tipo de movimiento", example = "ENTRADA")
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "ENTRADA|SALIDA", message = "Debe ser ENTRADA o SALIDA")
    private String tipoMovimiento;

    @Schema(description = "Cantidad movida", example = "10")
    @Positive(message = "Favor ingresar número válido")
    private int cantidad;

    @Schema(description = "Motivo del movimiento", example = "Compra inicial")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String motivo;
}
