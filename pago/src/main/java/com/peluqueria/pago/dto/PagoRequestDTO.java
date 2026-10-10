package com.peluqueria.pago.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de pago")
public class PagoRequestDTO {

    @Schema(description = "ID de la boleta a pagar", example = "1")
    @NotNull(message = "La boleta es obligatoria")
    private Long idBoleta;

    @Schema(description = "RUT del cliente", example = "111111111")
    @NotBlank(message = "El cliente es obligatorio")
    private String rutCliente;

    @Schema(description = "Monto pagado (debe ser igual al total de la boleta)", example = "20500")
    @Positive(message = "Favor ingresar número válido")
    private int monto;

    @Schema(description = "Método de pago", example = "DEBITO")
    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "EFECTIVO|DEBITO|CREDITO|TRANSFERENCIA", message = "Método de pago inválido")
    private String metodoPago;
}
