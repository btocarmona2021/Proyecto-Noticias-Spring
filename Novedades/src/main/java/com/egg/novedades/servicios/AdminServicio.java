package com.egg.novedades.servicios;

import com.egg.novedades.entidades.Noticia;
import com.egg.novedades.entidades.Periodista;
import com.egg.novedades.entidades.Rol;
import com.egg.novedades.entidades.Usuario;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.repositorios.PeriodistaRepositorio;
import com.egg.novedades.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminServicio {
    @Autowired
    private PeriodistaRepositorio periodistaRep;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void modificarPeriodista(String id, String nombreUsuario, Double sueldoMensual, String activo) throws MisExcepciones {
        Boolean bandera = true;
        if (activo == null) {
            bandera = false;
        }
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MisExcepciones("El nombre de usuario no puede ser nulo");
        }
        Periodista periodista = new Periodista();
        Optional<Periodista> respuesta = periodistaRep.findById(id);
        if (respuesta.isPresent()) {
            periodista = respuesta.get();
        }
        periodista.setNombreUsuario(nombreUsuario);
        periodista.setSueldoMensual(sueldoMensual);
        periodista.setActivo(bandera);
        periodistaRep.save(periodista);
    }

    @Transactional
    public void usuarioAPeriodista(String id) throws MisExcepciones {
        Usuario usuario = usuarioRepositorio.getOne(id);
        Periodista periodista = new Periodista();

        if (usuario.getActivo() && periodistaRep.buscarxNombreUsuario(usuario.getNombreUsuario()) == null){
            periodista.setNombreUsuario(usuario.getNombreUsuario());
            periodista.setRol(Rol.PERIODISTA);
            periodista.setFechaAlta(usuario.getFechaAlta());
            periodista.setActivo(usuario.getActivo());
            periodista.setPassword(usuario.getPassword());
            periodista.setMisNoticias(new ArrayList<Noticia>());
            periodista.setSueldoMensual(0.0);
            periodistaRep.save(periodista);
            usuario.setActivo(false);
            usuarioRepositorio.save(usuario);
        } else{
            periodista=periodistaRep.buscarxNombreUsuario(usuario.getNombreUsuario());
            periodista.setActivo(true);
            periodistaRep.save(periodista);
            usuario.setActivo(false);
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void periodistaAUsuario(String id) throws MisExcepciones {
        Periodista periodista = periodistaRep.getOne(id);
        Usuario usuario = usuarioRepositorio.buscarxNombreUsuarioDesactivado(periodista.getNombreUsuario());
        usuario.setActivo(true);
        usuarioRepositorio.save(usuario);
        periodista.setActivo(false);
        periodistaRep.save(periodista);
    }

}
