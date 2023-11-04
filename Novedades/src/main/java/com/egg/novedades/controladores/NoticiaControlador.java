package com.egg.novedades.controladores;

import com.egg.novedades.entidades.Categoria;
import com.egg.novedades.entidades.Noticia;
import com.egg.novedades.entidades.Periodista;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.servicios.CategoriaServicio;
import com.egg.novedades.servicios.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;/*
import org.springframework.security.access.prepost.PreAuthorize;*/
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServ;
    @Autowired
    CategoriaServicio categoriaServ;

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @GetMapping("/registrar")
    public String registraNoticia(ModelMap model) {
        List<Categoria> listaCategorias = categoriaServ.listadoCategorias();
        model.addAttribute("listadocategorias", listaCategorias);
        return ("novedades.html");
    }

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @PostMapping("/registro")
    public String registro(String titulo, String cuerpo, String imagen, String idCategoria, String estado, HttpSession session, ModelMap model) {
       Periodista creador = (Periodista)session.getAttribute("session");
        try {
            noticiaServ.crearNoticia(titulo, cuerpo, imagen, idCategoria,estado,creador);
            model.put("exito", "¡Agregado!");
            return "index.html";
        } catch (MisExcepciones ex) {
            model.put("error", ex.getMessage());
            return "crear_not_admin.html";
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @GetMapping("/listar")
    public String listarNoticias(ModelMap model) {
        List<Noticia> listadonoticias = noticiaServ.listarNoticias();

        model.addAttribute("listanoticias", listadonoticias);

        return "listar_noticias_admin.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
        @GetMapping("/eliminadas")
    public String noticiasEliminadas(ModelMap model) {
        List<Noticia> listadonoticias = noticiaServ.listarNoticiaseliminadas();

        model.addAttribute("listanoticias", listadonoticias);

        return "listar_eliminadas.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        Noticia noticia = noticiaServ.obtenerNoticia(id);
        List<Categoria> listacategoria = categoriaServ.listadoCategorias();
        model.put("noticia", noticia);
        model.addAttribute("listadocategorias",listacategoria);
        return "noticia_mod.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @PostMapping("/modificar/{id}")
    public String modificaNoticia(@PathVariable String id, String titulo, String cuerpo, String imagen,String idCategoria,String estado ,ModelMap model) {

        try {
            noticiaServ.modificarNoticia(id, titulo, cuerpo, imagen,idCategoria,estado);
            return "redirect:../listar";
        } catch (MisExcepciones ex) {
            model.put("error", ex.getMessage());
            return "noticia_mod.html";
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable String id, ModelMap model) {
        try {
            noticiaServ.eliminarNoticia(id);
            return "redirect:../listar";
        } catch (MisExcepciones ex) {
            model.put("error", ex.getMessage());
            return "redirect:../listar";
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @GetMapping("/habilitar/{id}")
    public String habilitarNoticia(@PathVariable String id, ModelMap model) {
        try {
            noticiaServ.habilitarNoticia(id);
            return "redirect:../eliminadas";
        } catch (MisExcepciones ex) {
            model.put("error", ex.getMessage());
            return "redirect:../listar";
        }

    }


    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA','USUARIO')")
    @GetMapping("/portal")
    public String portal(ModelMap model) {
        List<Noticia> listado = noticiaServ.listarNoticias();
        List<Categoria> listaCat= categoriaServ.listadoCategorias();
        model.addAttribute("listado", listado);
        model.addAttribute("categoria", listaCat);
        return "portal.html";
    }
}
