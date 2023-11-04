package com.egg.novedades.servicios;

import com.egg.novedades.entidades.Rol;
import com.egg.novedades.entidades.Usuario;
import com.egg.novedades.excepciones.MisExcepciones;
import com.egg.novedades.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRep;

    @Transactional
    public void crearUsuario(String nombreUsuario, String password, String verificacion) throws MisExcepciones {
        validarDatos(nombreUsuario, password, verificacion);
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setFechaAlta(new Date());
        usuario.setRol(Rol.USUARIO);
        usuario.setActivo(true);
        usuarioRep.save(usuario);
    }

    @Transactional
    public void modificarUsuario(String id, String nombreUsuario) throws MisExcepciones {
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MisExcepciones("El nombre de usuario no puede ser nulo");
        }
        Usuario usuario = new Usuario();
        Optional<Usuario> respuesta = usuarioRep.findById(id);
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
        }
        usuario.setNombreUsuario(nombreUsuario);
        usuarioRep.save(usuario);
    }

    @Transactional
    public void modificarUsuario_admin(String id, String nombreUsuario) throws MisExcepciones {
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MisExcepciones("El nombre de usuario no puede ser nulo");
        }
        Usuario usuario = new Usuario();
        Optional<Usuario> respuesta = usuarioRep.findById(id);
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
        }
        usuario.setNombreUsuario(nombreUsuario);
        usuarioRep.save(usuario);
    }

    public List listarUsuario() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios = usuarioRep.findAll();
        return listaUsuarios;
    }

    public Usuario buscauno(String id) {
        Usuario usuario = usuarioRep.getOne(id);
        return usuario;
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
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {

        Usuario usuario = usuarioRep.buscarxNombreUsuario(nombreUsuario);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("session", usuario);

            return new User(usuario.getNombreUsuario(), usuario.getPassword(), permisos);
        } else {
            return null;
        }

    }
}
