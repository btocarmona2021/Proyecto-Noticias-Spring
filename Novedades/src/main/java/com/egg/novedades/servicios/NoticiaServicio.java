package com.egg.novedades.servicios;

import com.egg.novedades.entidades.Categoria;
import com.egg.novedades.entidades.Noticia;
import com.egg.novedades.entidades.Periodista;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.repositorios.CategoriaRepositorio;
import com.egg.novedades.repositorios.NoticiaRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.egg.novedades.repositorios.PeriodistaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRep;
    @Autowired
    private CategoriaRepositorio categoriaRep;
    @Autowired
    private PeriodistaRepositorio periodistaRep;
    Boolean bandera = true;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo, String imagen, String idCategoria, String estado, Periodista creador) throws MisExcepciones {
        if (estado == null) {
            bandera = false;
        }
        List<Noticia> misnoticias = creador.getMisNoticias();
        Categoria categoria = categoriaRep.getById(idCategoria);
        validarDatos(titulo, cuerpo);
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFecha(new Date());
        noticia.setImagen(imagen);
        noticia.setCategoria(categoria);
        noticia.setEstado(bandera);
        noticia.setCreador(creador);
        noticiaRep.save(noticia);
        misnoticias.add(noticia);
        creador.setMisNoticias(misnoticias);
        periodistaRep.save(creador);

    }

    public List listarNoticias() {
        List<Noticia> listanoticias = noticiaRep.noticiasHabilitadas();
        return listanoticias;
    }

    public List noticiasxCategoria(String categoria) {
        List<Noticia> listado = new ArrayList<>();
        listado = noticiaRep.noticiasxCat(categoria);
        return listado;
    }

    public List listarNoticiaseliminadas() {
        List<Noticia> listanoticias = noticiaRep.noticiaseliminadas();
        return listanoticias;
    }


    public void modificarNoticia(String id, String titulo, String cuerpo, String imagen, String idCategoria, String estado) throws MisExcepciones {
        Categoria categoria = categoriaRep.getById(idCategoria);

        if (estado == null) {
            bandera = false;
        }
        validarDatos(titulo, cuerpo);
        System.out.println(imagen + idCategoria);
        Optional<Noticia> respuesta = noticiaRep.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setImagen(imagen);
            noticia.setCategoria(categoria);
            noticia.setEstado(bandera);
            noticiaRep.save(noticia);

        }
    }

    @Transactional
    public void eliminarNoticia(String id) throws MisExcepciones {
        if (id.isEmpty() || id == null) {
            throw new MisExcepciones("El id del autor no puede estar vacio o nulo");
        }
        Optional<Noticia> respuesta = noticiaRep.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setEstado(false);
            noticiaRep.save(noticia);
        }

    }

    @Transactional
    public void habilitarNoticia(String id) throws MisExcepciones {
        if (id.isEmpty() || id == null) {
            throw new MisExcepciones("El id del autor no puede estar vacio o nulo");
        }
        Optional<Noticia> respuesta = noticiaRep.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setEstado(true);
            noticiaRep.save(noticia);
        }

    }


    public void validarDatos(String titulo, String cuerpo) throws MisExcepciones {

        if (titulo.isEmpty() || titulo == null) {
            throw new MisExcepciones("El título no puede estar vacio o nulo");
        }
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MisExcepciones("El cuerpo de la noticia no puede esta vacio o nulo");
        }

    }

    public Noticia obtenerNoticia(String id) {
        Noticia noticia = noticiaRep.getOne(id);

        return noticia;
    }

}
