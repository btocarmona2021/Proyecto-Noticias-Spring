package com.egg.novedades.controladores;

import com.egg.novedades.entidades.Categoria;
import com.egg.novedades.entidades.Periodista;
import com.egg.novedades.entidades.Usuario;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.servicios.AdminServicio;
import com.egg.novedades.servicios.CategoriaServicio;
import com.egg.novedades.servicios.PeriodistaServicio;
import com.egg.novedades.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;/*
import org.springframework.security.access.prepost.PreAuthorize;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    private PeriodistaServicio periodistaServ;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private AdminServicio adminServ;
    @Autowired
    private CategoriaServicio categoriaServ;


    @GetMapping("/listarusuarios")
    public String listarPeriodistas(ModelMap model) {
        List<Usuario> listausuarios = usuarioServicio.listarUsuario();
        List<Periodista> listarperiodistas = periodistaServ.listarPeriodista();
        model.addAttribute("listausuarios", listausuarios);
        model.addAttribute("listarperiodistas",listarperiodistas);
        return "listarusuarios.html";
    }

    @GetMapping("/modificarUsuario/{id}")
    public String modUsuario(@PathVariable String id, ModelMap model) {
        Usuario usuario = usuarioServicio.buscauno(id);
        model.put("usuario", usuario);
        return "mod_usuario_admin.html";
    }
    @GetMapping("/modificarperiodista/{id}")
    public String modPeriodista(@PathVariable String id, ModelMap model) {
        Periodista usuario = periodistaServ.obtenerUno(id);
        model.put("usuario", usuario);
        return "mod_periodista_admin.html";
    }
    @PostMapping("modificadop")
    public String modificadop(@RequestParam String id, @RequestParam String nombreUsuario, Double sueldoMensual, String activo, ModelMap model) {
        try {
            adminServ.periodistaAUsuario(id);
/*adminServ.modificarPeriodista(id, nombreUsuario, sueldoMensual, activo);*/
            model.put("exito", "El usuario se ha modificado correctamente");
            return "redirect:/admin/listarusuarios";
        } catch (MisExcepciones e) {
            model.put("error", e.getMessage());
            return "mod_usuario_admin.html";
        }
    }




    @PostMapping("modificado")
    public String modificado(@RequestParam String id, @RequestParam String nombreUsuario, Double sueldoMensual, String activo, ModelMap model) {
        try {
            adminServ.usuarioAPeriodista(id);
//            adminServ.modificarPeriodista(id, nombreUsuario, sueldoMensual, activo);
            model.put("exito", "El usuario se ha modificado correctamente");
            return "redirect:/admin/listarusuarios";
        } catch (MisExcepciones e) {
            model.put("error", e.getMessage());
            return "mod_usuario_admin.html";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, ModelMap model) {
        Usuario usuario = (Usuario) session.getAttribute("session");
        model.put("usuario",usuario);
        return "dashboard.html";
    }

    @GetMapping("/crearnoticia")
    public String crearnoticia(HttpSession session, ModelMap model) {
        List<Categoria> listaCat = categoriaServ.listadoCategorias();
        model.addAttribute("categorias", listaCat);
        return "crear_not_admin.html";
    }
}

