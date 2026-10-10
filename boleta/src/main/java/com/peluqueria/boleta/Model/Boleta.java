package com.peluqueria.boleta.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BOLETA")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoleta;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false, length = 12)
    private String rutCliente;

    @Column(nullable = false, length = 12)
    private String rutProfesional;

    private Integer idAgenda;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false, length = 10)
    private String estadoBoleta;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleBoleta> detalles = new ArrayList<>();

    public void agregarDetalle(DetalleBoleta detalle) {
        detalle.setBoleta(this);
        detalles.add(detalle);
    }
}
