package com.egg.novedades.controladores;

import com.egg.novedades.entidades.Usuario;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServ;

    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap model) {
        Usuario usuario = (Usuario) session.getAttribute("session");
        model.put("usuario", usuario);
        return "perfil.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificarUsuario(@PathVariable String id, ModelMap model) {
        Usuario usuario = usuarioServ.buscauno(id);
        model.put("usuario", usuario);
        return "mod_usuario.html";
    }

    @PostMapping("modificado")
    public String modificado(String id,String nombreUsuario,ModelMap model){
        try {
            usuarioServ.modificarUsuario(id,nombreUsuario);
            model.put("exito","Se han modificado los datos del usuario correctamente");
            return "redirect:/usuario/perfil";
        } catch (MisExcepciones e) {
            throw new RuntimeException(e);
        }

    }
}
