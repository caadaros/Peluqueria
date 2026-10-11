package com.peluqueria.profesional.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Obligatorio, para indicar que la clase que viene será un tabla
@Table(name = "Profesional") // Opcional, para especificar los detalles de la tabla en BD
public class Profesional {
    //idTipoServicio se crea como PK, autoincrementable
    @Id
    private String rutProfesional;

    //No puede quedar vacío y deben ser datos únicos
    @Column(nullable = false, length = 100)
    private String nombreProfesional;

    //Tampoco puede quedar vacío
    @Column(nullable = false, length = 100)
    private String apellidoProfesional;
    
    @Column(nullable = false, length = 9)
    private String telefonoProfesional;

    @Column(nullable = false, length = 100, unique = true)
    private String correoProfesional;

    @Column(nullable = true, length = 100)
    private String especialidad;

    @Column(nullable = false, length = 15)
    private String estado;

}

