package com.peluqueria.producto.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false, length = 50, unique = true)
    private String skuProducto;

    @Column(nullable = false, length = 100)
    private String nombreProducto;

    @Column(length = 255)
    private String descripcionProducto;

    @Column(nullable = false)
    private int precioProducto;

    @Column(nullable = false, length = 15)
    private String estado;

}
