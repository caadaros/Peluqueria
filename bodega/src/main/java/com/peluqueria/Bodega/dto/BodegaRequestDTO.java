package com.peluqueria.bodega.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de una bodega")
public class BodegaRequestDTO {

    @Schema(description = "Nombre de la bodega", example = "Bodega Central")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcionBodega;


    @Schema(description = "Ubicación de la bodega", example = "Piso 1, Sector A")
    @NotBlank(message = "La ubicación no puede estar vacía")
    private String ubicacionBodega;
}
