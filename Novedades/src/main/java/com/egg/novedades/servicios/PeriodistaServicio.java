package com.egg.novedades.servicios;

import com.egg.novedades.entidades.Periodista;
import com.egg.novedades.entidades.Rol;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.repositorios.PeriodistaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//NUEVO COMENTARIO

@Service
public class PeriodistaServicio {
    @Autowired
    private PeriodistaRepositorio periodistaRep;

    @Transactional
    public void crearPeriodista(String nombreUsuario, String password, String verificacion)throws MisExcepciones {
        validarDatos(nombreUsuario, password, verificacion);
        Periodista periodista = new Periodista();
        periodista.setNombreUsuario(nombreUsuario);
        periodista.setPassword(new BCryptPasswordEncoder().encode(password));
        periodista.setFechaAlta(new Date());
        periodista.setRol(Rol.PERIODISTA);
        periodista.setActivo(true);
        periodista.setMisNoticias(new ArrayList<>());
        periodista.setSueldoMensual(0.0);
        periodistaRep.save(periodista);
    }

    public List<Periodista> listarPeriodista() {
        List<Periodista> listaperiodista;
        listaperiodista = periodistaRep.findAll();
        return listaperiodista;
    }

    public Periodista obtenerUno(String id) {
        Periodista periodista;
        periodista = periodistaRep.getOne(id);
        return periodista;
    }
    public void validarDatos(String nombreUsuario, String password, String verificacion) throws MisExcepciones {
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MisExcepciones("El nombre de usuario no puede ser nulo");
        }
        if (!password.equals(verificacion)) {
            throw new MisExcepciones("Las contraseñas no coinciden");
        }
        if (password.length() < 5) {
            throw new MisExcepciones("El password debe tener al menos 5 caracteres");
        }
    }

}
