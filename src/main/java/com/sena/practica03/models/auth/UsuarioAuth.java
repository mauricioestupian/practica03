package com.sena.practica03.models.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.sena.practica03.models.usuarios.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "usuarios_auth")
@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class UsuarioAuth {
    @Id 
    private Long id;

    //Realcion con Usuario es decir el perfil
    @MapsId //permite que usuarioAuth comparta identificador con Usuario
    @OneToOne (fetch = FetchType.LAZY)// Cada Usuario tiene un unico registro de atenticación 
    @JoinColumn (name = "usuario_id", nullable = false)
    private Usuario usuario;

    //Credenciales de acceso
    @Column(name="username",  nullable=false,unique=true, length = 80)
    private String user;

    @Column (name = "passwprd", nullable = false)// no lleva longitud debido que es un dato encriptado 
    private String pass;

    //Roles asignados al usuario
    @ManyToMany ()//Un usuario puede tener varios roles
    @JoinTable (
        name = "usuarios_auth_roles",
        joinColumns = @JoinColumn (name = "usuario_auth_id"),
        inverseJoinColumns = @JoinColumn (name = "rol_id") 
    )
    @Builder .Default
    private Set<Rol> roles = new HashSet<>();

    //Estado de cuenta
    @Column (name = "habilitado", nullable = false)
    private boolean habilidato = true;
}
