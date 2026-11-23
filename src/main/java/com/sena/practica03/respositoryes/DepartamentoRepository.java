package com.sena.practica03.respositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.practica03.models.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, String> {

}
