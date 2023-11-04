package com.egg.novedades.repositorios;

import com.egg.novedades.entidades.Periodista;
import com.egg.novedades.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeriodistaRepositorio extends JpaRepository<Periodista,String> {

    @Query("SELECT p FROM Periodista p WHERE p.nombreUsuario = :nombreUsuario")
    public Periodista buscarxNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
}
