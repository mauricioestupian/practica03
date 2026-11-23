package com.sena.practica03.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departamentos")
@Getter
// El accessLevel.PROTECTED en el constructor sin argumentos evita que se pueda
// instanciar directamente la clase desde fuera del paquete, promoviendo el uso
// de un patrón de diseño adecuado para la creación de objetos.
// es decir hace efectivamente inmutable en la clase, ya que no se puede crear
// una instancia de la clase directamente desde fuera del paquete, lo que ayuda
// a mantener la integridad de los datos y a evitar modificaciones no deseadas.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Departamento {

    @Id
    @Column(name = "codigo", length = 2)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;
}