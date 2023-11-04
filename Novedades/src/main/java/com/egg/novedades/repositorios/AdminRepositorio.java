package com.egg.novedades.repositorios;

import com.egg.novedades.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepositorio  extends JpaRepository<Admin,String> {

}
