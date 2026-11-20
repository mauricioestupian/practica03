package com.sena.practica03.respositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.practica03.models.auth.UsuarioAuth;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {

    // metodo para verificar si un usuario ya existe en la base de datos
    boolean existsByUser(String user);

}
