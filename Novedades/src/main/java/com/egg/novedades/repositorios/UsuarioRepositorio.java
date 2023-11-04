package com.egg.novedades.repositorios;

import com.egg.novedades.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.activo = true")
    public Usuario buscarxNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.activo = false ")
    public Usuario buscarxNombreUsuarioDesactivado(@Param("nombreUsuario") String nombreUsuario);

}
