package com.peluqueria.kardex.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MOVIMIENTO_KARDEX")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @Column(nullable = false)
    private Long idProducto;

    @Column(nullable = false)
    private Long idBodega;

    @Column(nullable = false, length = 10)
    private String tipoMovimiento;

    @Column(nullable = false)
    private int cantidad;

    @Column(length = 200)
    private String motivo;

    @Column(nullable = false)
    private LocalDateTime fechaMovimiento;

    @Column(length = 50)
    private String registradoPor;

}
