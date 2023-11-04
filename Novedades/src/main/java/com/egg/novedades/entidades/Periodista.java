package com.egg.novedades.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Periodista extends Usuario {

    @OneToMany(mappedBy = "creador", fetch = FetchType.EAGER)
    private List<Noticia> misNoticias;

    private Double sueldoMensual;

    public Periodista() {
    }
}
