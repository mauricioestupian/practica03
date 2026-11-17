package com.sena.practica03.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.practica03.models.Usuario;

@Repository 
public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {

}
