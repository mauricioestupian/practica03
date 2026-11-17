package com.sena.practica03.models.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity 
@Table (name = "clientes")
@PrimaryKeyJoinColumn (name = "cliente_id")
@Getter @Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@SuperBuilder 
public class Cliente extends Usuario {
    //Si hacemos uso de Enum TipoCliente
    //@Enumerated (EnumType.ORDINAL)Extrae del Enum id
    //@Enumerated (EnumType.STRING)Extrae del Enum texto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "tipo_Cliente", nullable = false)
    private TiposCliente tipoCliente;

    @Column (name = "puntos")
    private Integer puntos;

}
