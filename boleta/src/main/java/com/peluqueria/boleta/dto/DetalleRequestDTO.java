package com.peluqueria.boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Línea de la boleta: un servicio o un producto")
public class DetalleRequestDTO {

    @Schema(description = "Tipo de ítem", example = "SERVICIO")
    @NotBlank(message = "El tipo de ítem es obligatorio")
    @Pattern(regexp = "SERVICIO|PRODUCTO", message = "Debe ser SERVICIO o PRODUCTO")
    private String tipoItem;

    @Schema(description = "ID del tipo de servicio o del producto", example = "1")
    @NotNull(message = "El ítem es obligatorio")
    private Long idItem;

    @Schema(description = "Cantidad", example = "1")
    @Positive(message = "Favor ingresar número válido")
    private int cantidad;
}
