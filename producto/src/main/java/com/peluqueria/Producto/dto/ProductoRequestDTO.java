package com.peluqueria.producto.dto;

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
@Schema(description = "DTO para la solicitud de creación o actualización de un producto")
public class ProductoRequestDTO {

    @Schema(description = "Nombre del producto", example = "Shampoo Keratina 500ml")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcionProducto;


    @Schema(description = "Precio del producto", example = "10000")
    @Positive(message = "Favor ingresar precio válido")
    private int precioProducto; 

    @Schema(description = "Unidad de medida del producto", example = "Unidad")
    @NotBlank(message = "La unidad de medida no puede estar vacía")
    private String unidadMedida;
}
