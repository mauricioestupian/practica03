package com.sena.practica03.respositoryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.practica03.models.auth.Rol;

public interface RolRepository extends JpaRepository<Rol, Byte> {

    // metodo para buscar un rol por su nombre
    Optional<Rol> findByNombre(String nombre);

}
