package com.sena.practica03.respositoryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.practica03.models.usuarios.TipoDocumento;
import com.sena.practica03.models.usuarios.Usuario;

public interface UsuarioRespository extends JpaRepository<Usuario, Long> {
    // metodo para verificar si un usuario ya existe en la base de datos
    // esto es para validar que no se repita el tipo de documento y el numero de
    // documento
    boolean existsByTipoDocAndNumDoc(TipoDocumento tipoDoc, String numDoc);

    // Metodo para buscar un usuario por numero de documento
    Usuario findByNumDoc(String numDoc);

    // buscar un usuario por numero de documento usando una consulta nativa
    @Query(value = "SELECT * FROM usuarios WHERE num_doc = :numDoc", nativeQuery = true)
    Optional<Usuario> findByNumDocNativo(@Param("numDoc") String numDoc);
}
