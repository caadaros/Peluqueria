package com.peluqueria.notificacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la solicitud de creación o actualización de notificacion")
public class NotificacionRequestDTO {

    @Schema(description = "Tipo de notificación", example = "CONFIRMACION_RESERVA")
    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "CONFIRMACION_RESERVA|RECORDATORIO_CITA|CANCELACION|ESTADO_PAGO", message = "Tipo de notificación inválido")
    private String tipoNotificacion;

    @Schema(description = "RUT del cliente", example = "111111111")
    @NotBlank(message = "El cliente es obligatorio")
    private String rutCliente;

    @Schema(description = "Detalle adicional del mensaje", example = "Cita del 2026-05-25 a las 09:00.")
    @Size(max = 300, message = "Máximo 300 caracteres")
    private String detalle;
}
