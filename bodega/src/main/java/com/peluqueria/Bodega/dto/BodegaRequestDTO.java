package com.peluqueria.bodega.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de bodega")
public class BodegaRequestDTO {

    @Schema(description = "Nombre de la bodega", example = "Bodega central")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreBodega;

    @Schema(description = "Dirección de la bodega", example = "Av. Principal 123")
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccionBodega;

    @Schema(description = "Estado de la bodega", example = "Activa")
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
