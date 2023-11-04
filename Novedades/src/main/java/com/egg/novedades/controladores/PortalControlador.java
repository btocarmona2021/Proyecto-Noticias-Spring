package com.egg.novedades.controladores;

import com.egg.novedades.entidades.Categoria;
import com.egg.novedades.entidades.Noticia;
import com.egg.novedades.entidades.Periodista;
import com.egg.novedades.entidades.Usuario;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.servicios.CategoriaServicio;
import com.egg.novedades.servicios.NoticiaServicio;

import java.util.ArrayList;
import java.util.List;

import com.egg.novedades.servicios.PeriodistaServicio;
import com.egg.novedades.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private NoticiaServicio noticiaServ;
    @Autowired
    private CategoriaServicio categoriaServ;
    @Autowired
    private UsuarioServicio usuarioServ;
    @Autowired
    private PeriodistaServicio periodistaServ;

    @GetMapping("/")
    public String index(ModelMap model) {
        List<Categoria> listaCat = categoriaServ.listadoCategorias();
        model.addAttribute("categoria", listaCat);
        return "index.html";
    }

    @PostMapping("/buscar")
    public String buscar(String categoria, ModelMap model) {

        List<Noticia> listado = noticiaServ.noticiasxCategoria(categoria);
        List<Categoria> listaCat = categoriaServ.listadoCategorias();
        if (!listado.isEmpty()) {
            model.put("resultado", "Se han encontrado los siguientes resultados en la categoria: " + categoria.toUpperCase());
        }
        model.addAttribute("listado", listado);
        model.addAttribute("categoria", listaCat);
        return "portal.html";
    }

    @GetMapping("/registrar")
    public String registra() {
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreUsuario, @RequestParam String password, @RequestParam String verificacion, ModelMap model) {
        try {
            usuarioServ.crearUsuario(nombreUsuario, password, verificacion);
            model.put("exito", "El usuario se ha creado!");
            return "redirect:/";
        } catch (MisExcepciones e) {
            model.put("error", e.getMessage());
            model.put("nombreUsuario", nombreUsuario);
            return "registrar.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,ModelMap model) {
        if (error != null){
            model.put("error","Usuario o Contraseña incorrectos");
        }
        return "login.html";
    }


    @GetMapping("/verificar")
    public String verificar(HttpSession session, ModelMap model) {
        Periodista per = (Periodista) session.getAttribute("session");

        List<Noticia> noticias= per.getMisNoticias();
        model.addAttribute("noticias",noticias);
        return "verificar.html";
    }

}
