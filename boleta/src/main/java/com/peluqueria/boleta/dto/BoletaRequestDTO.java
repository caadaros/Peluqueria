package com.peluqueria.boleta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la emisión de una boleta")
public class BoletaRequestDTO {

    @Schema(description = "RUT del cliente", example = "111111111")
    @NotBlank(message = "El cliente es obligatorio")
    private String rutCliente;

    @Schema(description = "RUT del profesional", example = "222222221")
    @NotBlank(message = "El profesional es obligatorio")
    private String rutProfesional;

    @Schema(description = "ID de la cita asociada (opcional)", example = "1")
    private Integer idAgenda;

    @NotEmpty(message = "La boleta debe tener al menos un detalle")
    @Valid
    private List<DetalleRequestDTO> detalles;
}
