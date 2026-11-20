package com.sena.practica03.models.auth;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sena.practica03.models.usuarios.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioAuth {
    @Id
    private Long id;

    // Realcion con Usuario es decir el perfil
    @MapsId // permite que usuarioAuth comparta identificador con Usuario
    @OneToOne(fetch = FetchType.LAZY) // Cada Usuario tiene un unico registro de atenticación
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Credenciales de acceso
    @Column(name = "username", nullable = false, unique = true, length = 80)
    private String user;

    @Column(name = "password", nullable = false) // no lleva longitud debido que es un dato encriptado
    private String pass;

    // Estado general de la cuenta
    // Cuenta deshabilitada por un administrador: afecta a toda la cuenta
    // El uso de @Builder.Default permite establecer un valor predeterminado para el
    // campo habilitado cuando se utiliza el patrón de diseño Builder para crear
    // instancias de la clase UsuarioAuth. En este caso, se establece que el valor
    // predeterminado de habilitado sea true, lo que significa que, por defecto, la
    // cuenta de usuario estará habilitada a menos que se especifique lo contrario
    // al construir el objeto.
    @Builder.Default
    @Column(name = "habilitado", nullable = false)
    private boolean habilitado = true;

    // Control de intentos fallidos
    // # intentos fallidos: genera un bloqueo temporal de acceso a la cuenta
    @Builder.Default
    @Column(name = "intentos_fallidos", nullable = false)
    private Integer intentosFallidos = 0;

    // Fecha hasta la cual permanece bloqueada la cuenta
    @Column(name = "bloqueado_hasta")
    private LocalDateTime bloqueadoHasta;

    // Asignaciones de roles
    @OneToMany(mappedBy = "usuarioAuth", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UsuarioAuthRol> roles = new HashSet<>();

}
