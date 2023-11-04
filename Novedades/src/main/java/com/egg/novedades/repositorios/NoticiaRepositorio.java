




package com.egg.novedades.repositorios;

import com.egg.novedades.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String>{

    
    @Query("SELECT n FROM Noticia n WHERE n.estado = true ORDER BY n.fecha DESC")
    public List<Noticia> noticiasHabilitadas();
    
    
    @Query("SELECT n FROM Noticia n WHERE n.estado = false ORDER BY n.fecha DESC")
    public List<Noticia> noticiaseliminadas();
    
    @Query("SELECT n FROM Noticia n WHERE n.categoria.nombre = :categoria AND n.estado = true")
    public List<Noticia> noticiasxCat(@Param("categoria") String categoria);
    
}
