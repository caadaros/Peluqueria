package com.peluqueria.bodega.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BODEGA")
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBodega;

    @Column(nullable = false, length = 100, unique = true)
    private String nombreBodega;

    @Column(nullable = false, length = 200)
    private String direccionBodega;

    @Column(nullable = false, length = 15)
    private String estado;

}
