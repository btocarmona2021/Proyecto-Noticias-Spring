package com.egg.novedades.controladores;

import com.egg.novedades.entidades.Categoria;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.servicios.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categoria")
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaServ;

    @PreAuthorize("hasAnyRole('ADMIN','PERIODISTA')")
    @GetMapping("/registrar")
    public String registrarCategoria() {
        return "registrar_cat.html";
    }
    @PostMapping("/registro")
    public String categoriaRegistrada(String nombre,String imagen_cat, ModelMap model) throws MisExcepciones{
        try {
            Categoria categoria = categoriaServ.crearCategoria(nombre,imagen_cat);
            model.put("exito", "Se ha creado la categoría correctamente");

        } catch (MisExcepciones ex) {
            model.put("error", ex.getMessage());
            return "resgistrar_cat.html";
        }
        return "index.html";
    }
}
