package com.egg.novedades.servicios;

import com.egg.novedades.entidades.Categoria;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.repositorios.CategoriaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRep;

    @Transactional
    public Categoria crearCategoria(String nombre, String imagen_cat) throws MisExcepciones {

        validar(nombre);
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setImagen_cat(imagen_cat);
        categoriaRep.save(categoria);
        return categoria;
    }

    public void validar(String nombre) throws MisExcepciones {
        if (nombre.isEmpty() || nombre == null) {
            throw new MisExcepciones("El nombre de la categoría no puede estar vacio o nulo");
        }
    }

    @Transactional
    public void modificarCategoria(String id, String nombre, String imagen_cat) throws MisExcepciones {
        validar(nombre);
        Optional<Categoria> respuesta = categoriaRep.findById(id);
        if (respuesta.isPresent()) {
            Categoria categoria = respuesta.get();
            categoria.setNombre(nombre);
            categoria.setImagen_cat(imagen_cat);
            categoriaRep.save(categoria);
        }
    }

    public List listadoCategorias() {
        List<Categoria> listadoCattegorias = new ArrayList<>();
        listadoCattegorias = categoriaRep.findAll();
        return listadoCattegorias;
    }

    public Categoria obtenerCategoria(String id) {
        Categoria categoria = categoriaRep.getOne(id);
        return categoria;
    }

}
