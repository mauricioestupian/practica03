package com.sena.practica03.respositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.practica03.models.usuarios.CargosTrabajador;

public interface CargoTrabajadorRepository  extends JpaRepository<CargosTrabajador, Byte> {

}
