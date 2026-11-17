package com.sena.practica03.models.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Table (name = "cargo_trabajador")
@Getter 
@NoArgsConstructor 
public class CargosTrabajador {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column (nullable = false, unique = true)
    private String cargoTrab;

}
