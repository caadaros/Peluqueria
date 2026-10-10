package com.peluqueria.notificacion.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTIFICACION")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @Column(nullable = false, length = 25)
    private String tipoNotificacion;

    @Column(nullable = false, length = 12)
    private String rutCliente;

    @Column(length = 300)
    private String detalle;

    @Column(nullable = false, length = 100)
    private String correoDestino;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false, length = 10)
    private String estadoEnvio;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

}
