package com.peluqueria.notificacion.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponseDTO {
    private Long idNotificacion;
    private String tipoNotificacion;
    private String rutCliente;
    private String detalle;
    private String correoDestino;
    private String mensaje;
    private String estadoEnvio;
    private LocalDateTime fechaEnvio;
}
