package com.egg.novedades.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter

public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String Id;
    protected String nombreUsuario;
    protected String password;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaAlta;

    @Enumerated(EnumType.STRING)
    protected Rol rol;
    protected Boolean activo;

    public Usuario() {
    }
}

