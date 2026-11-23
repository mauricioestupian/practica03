package com.sena.practica03.respositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.practica03.models.usuarios.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    // Consulta trabajador por numero de documento y tipo de documento teniendo en
    // que se hereda de Usuario, por lo que se debe buscar en la tabla de Usuario.
    Trabajador findByTipoDocumentoIdAndNumDocumento(Byte tipoDocId, String numDoc);

}
