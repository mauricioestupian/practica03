package com.sena.practica03.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ciudades")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ciudad {

    @Id
    @Column(name = "codigo", length = 5)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_codigo", nullable = false)
    private Departamento departamento;
}
