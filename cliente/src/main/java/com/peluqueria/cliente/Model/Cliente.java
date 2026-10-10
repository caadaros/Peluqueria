package com.peluqueria.cliente.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENTE", schema = "PELUQUERIA") // <-- Agregado schema y nombre en mayúsculas
public class Cliente {
    
    @Id
    @Column(name = "RUT_CLIENTE") // <-- Es recomendable mapear también la columna exacta si difiere
    private String rutCliente;

    @Column(name = "NOMBRE_CLIENTE", nullable = false, length = 100)
    private String nombreCliente;

    @Column(name = "APELLIDO_CLIENTE", nullable = false, length = 100)
    private String apellidoCliente;
    
    @Column(name = "TELEFONO_CLIENTE", nullable = false, length = 9)
    private String telefonoCliente;

    @Column(name = "CORREO_CLIENTE", nullable = false, length = 100, unique = true)
    private String correoCliente;
}