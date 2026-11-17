package com.sena.practica03.models.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity 
@Table (name="usuarios")
@Inheritance (strategy = InheritanceType.JOINED)
@Getter @Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@SuperBuilder 
public class Usuario {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombres", nullable=false, length = 60) 
    private String nom;

    @Column(name="apellidos", nullable=false, length = 60) 
    private String ape;
    
    @Column(name="direccion", length = 120) 
    private String dir;

    @Column(name="telefono", length = 20)
    private String tel;

}
