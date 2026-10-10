package com.peluqueria.pago.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAGO")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @Column(nullable = false)
    private Long idBoleta;

    @Column(nullable = false, length = 12)
    private String rutCliente;

    @Column(nullable = false)
    private int monto;

    @Column(nullable = false, length = 15)
    private String metodoPago;

    @Column(nullable = false, length = 10)
    private String estadoPago;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

}
