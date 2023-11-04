package com.egg.novedades.entidades;

import java.util.Date;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Data
public class Noticia {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuid",strategy = "uuid2")
  
    private String id;
    private String titulo;
    private String cuerpo;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String imagen;
    @ManyToOne
    private Categoria categoria;

    private Boolean estado;

    @ManyToOne
    private Periodista creador;

    public Noticia() {

    }
}
