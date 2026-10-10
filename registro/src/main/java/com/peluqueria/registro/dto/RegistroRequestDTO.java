package com.peluqueria.registro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de registro")
public class RegistroRequestDTO {

    @Schema(description = "ID de la cita asociada (opcional)", example = "1")
    private Integer idAgenda;

    @Schema(description = "RUT del cliente", example = "111111111")
    @NotBlank(message = "El cliente es obligatorio")
    private String rutCliente;

    @Schema(description = "RUT del profesional", example = "222222221")
    @NotBlank(message = "El profesional es obligatorio")
    private String rutProfesional;

    @Schema(description = "ID del tipo de servicio", example = "1")
    @NotNull(message = "El servicio es obligatorio")
    private Long idTipoServicio;

    @Schema(description = "ID del producto utilizado (opcional)", example = "1")
    private Long idProducto;

    @Schema(description = "Cantidad de producto utilizado", example = "1")
    @Positive(message = "Favor ingresar número válido")
    private Integer cantidadProducto;

    @Schema(description = "Fecha de la atención", example = "2026-05-25")
    @NotBlank(message = "La fecha es obligatoria")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "La fecha debe tener formato yyyy-MM-dd")
    private String fecha;

    @Schema(description = "Observaciones de la atención", example = "Sin novedades")
    @Size(max = 500, message = "Máximo 500 caracteres")
    private String observaciones;
}
