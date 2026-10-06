package com.peluqueria.cliente.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Obligatorio, para indicar que la clase que viene será un tabla
@Table(name = "Cliente") // Opcional, para especificar los detalles de la tabla en BD
public class Cliente {
    //idTipoServicio se crea como PK, autoincrementable
    @Id
    private String rutCliente;

    //No puede quedar vacío y deben ser datos únicos
    @Column(nullable = false, length = 100)
    private String nombreCliente;

    //Tampoco puede quedar vacío
    @Column(nullable = false, length = 100)
    private String apellidoCliente;
    
    @Column(nullable = false, length = 9)
    private String telefonoCliente;

    @Column(nullable = false, length = 100, unique = true)
    private String correoCliente;
}

/*  Para relacionar tablas de una misma BD utilizamos 2 anotaciones:
    1. Muchos registros de una tabla están relacionados con un solo registro de otra tabla
    @ManyToOne
    @JoinColumn(name = "id_tipo_servicio") // Crea la llave foránea (FK) en la tabla
    
    2. Especificas que un elemento de esta tabla está realcionado con muchos registros de otra tabla
    @OneToMany(mappedBy = "cliente") 
    cliente sería desde donde se extrae la información
*/