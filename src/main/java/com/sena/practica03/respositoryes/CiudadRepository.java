package com.sena.practica03.respositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.practica03.models.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, String> {
    // Método para obtener todas las ciudades de un departamento específico
    List<Ciudad> findByDepartamentoCodigo(String codigo);

}
