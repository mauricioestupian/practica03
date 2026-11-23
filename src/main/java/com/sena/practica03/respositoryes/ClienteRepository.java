package com.sena.practica03.respositoryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.practica03.models.usuarios.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para buscar un cliente por su número de documento
    Optional<Cliente> findByNumDoc(String numDoc);

    // es posible que dos usuario compartan el mismo numero de documento, pero el
    // tipo de documento es unico, por lo que se puede buscar un usuario por su
    // numero de documento y tipo de documento
    Optional<Cliente> findByTipoDocIdAndNumDoc(String numDoc, Byte tipoDoc);

}
