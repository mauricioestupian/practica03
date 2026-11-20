package com.sena.practica03.models.auth;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios_auth_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioAuthRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con UsuarioAuth
    // esta relación indica que un UsuarioAuth puede tener múltiples roles
    // asignados, y cada asignación de rol está asociada a un único UsuarioAuth.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_auth_id", nullable = false)
    private UsuarioAuth usuarioAuth;

    // Relación con Rol
    // esta relación indica que un rol puede ser asignado a múltiples usuarios
    // autenticados.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    // Estado de la asignación
    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    // Fecha en que se asignó el rol
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion;

    // Fecha en que se retiró el rol
    @Column(name = "fecha_revocacion")
    private LocalDateTime fechaRevocacion;

    // Motivo u observación sobre la asignación o retiro
    @Column(name = "observacion", length = 250)
    private String observacion;
}
