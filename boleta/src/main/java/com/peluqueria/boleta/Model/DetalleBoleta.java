package com.peluqueria.boleta.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DETALLE_BOLETA")
public class DetalleBoleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_boleta")
    private Boleta boleta;

    @Column(nullable = false, length = 10)
    private String tipoItem;

    @Column(nullable = false)
    private Long idItem;

    @Column(nullable = false, length = 150)
    private String descripcion;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private int precioUnitario;

    @Column(nullable = false)
    private int subtotal;
}
