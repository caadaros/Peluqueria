package com.peluqueria.producto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de producto")
public class ProductoRequestDTO {

    @Schema(description = "SKU del producto", example = "SHAMP-001")
    @NotBlank(message = "El SKU no puede estar vacío")
    private String skuProducto;

    @Schema(description = "Nombre del producto", example = "Shampoo profesional")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreProducto;

    @Schema(description = "Descripción del producto", example = "500 ml")
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String descripcionProducto;

    @Schema(description = "Precio del producto", example = "8500")
    @Positive(message = "Favor ingresar número válido")
    private int precioProducto;

    @Schema(description = "Estado del producto", example = "Activo")
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
