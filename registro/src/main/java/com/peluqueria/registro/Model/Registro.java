package com.peluqueria.registro.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REGISTRO")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistro;

    private Integer idAgenda;

    @Column(nullable = false, length = 12)
    private String rutCliente;

    @Column(nullable = false, length = 12)
    private String rutProfesional;

    @Column(nullable = false)
    private Long idTipoServicio;

    private Long idProducto;

    private Integer cantidadProducto;

    @Column(nullable = false)
    private String fecha;

    @Column(length = 500)
    private String observaciones;

}
