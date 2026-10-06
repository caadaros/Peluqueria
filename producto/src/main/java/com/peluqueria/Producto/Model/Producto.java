package com.peluqueria.producto.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Obligatorio, para indicar que la clase que viene será un tabla
@Table(name = "PRODUCTOS")// Opcional, para especificar los detalles de la tabla en BD
public class Producto {
    //idProducto se crea como PK, autoincrementable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    //No puede quedar vacío y deben ser datos únicos
    @Column(nullable = false, length = 100, unique = true)
    private String descripcionProducto;

    //Tampoco puede quedar vacío
    @Column(nullable = false, length = 10)
    private int precioProducto;  

    //No puede quedar vacío
    @Column(nullable = false, length = 15)
    private String unidadMedida;

}

/*  Para relacionar tablas utilizamos 2 anotaciones:
    1. Muchos registros de una tabla están relacionados con un solo registro de otra tabla
    @ManyToOne
    @JoinColumn(name = "id_tipo_servicio") // Crea la llave foránea (FK) en la tabla
    
    2. Especificas que un elemento de esta tabla está realcionado con muchos registros de otra tabla
    @OneToMany(mappedBy = "cliente") 
    cliente sería desde donde se extrae la información
*/