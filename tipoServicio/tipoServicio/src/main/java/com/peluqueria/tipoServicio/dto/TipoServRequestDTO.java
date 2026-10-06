package com.peluqueria.tipoServicio.dto;

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
@Schema(description = "DTO para la solicitud de creación o actualización de un tipo de servicio")
public class TipoServRequestDTO {

    @Schema(description = "Nombre del tipo de servicio", example = "Corte de cabello")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcionServicio;


    @Schema(description = "Precio del tipo de servicio", example = "10000")
    @Positive(message = "Favor ingresar número válido")
    private int precioServicio; 

    @Schema(description = "Duración del servicio en horas", example = "1.5")
    @NotNull(message = "Debe ingresar la duración del servicio (en cantidad de horas)")    
    @Positive(message = "Favor ingresar número válido")
    private float duracionHoras;

    @Schema(description = "Especialidad asociada al tipo de servicio", example = "Peluquería")
    @NotBlank(message = "La especialidad no puede estar vacía")
    private String especialidadAsociada;
}
