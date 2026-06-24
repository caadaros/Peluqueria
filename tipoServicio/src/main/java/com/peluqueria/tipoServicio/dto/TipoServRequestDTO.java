package com.peluqueria.tipoServicio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoServRequestDTO {
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcionServicio;


    @Positive(message = "Favor ingresar número válido")
    private int precioServicio; 

    @NotNull(message = "Debe ingresar la duración del servicio (en cantidad de horas)")    
    @Positive(message = "Favor ingresar número válido")
    private float duracionHoras;

    @NotBlank(message = "La especialidad no puede estar vacía")
    private String especialidadAsociada;
}
