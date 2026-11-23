package com.sena.practica03.services;

import java.util.List;

import com.sena.practica03.dtos.TrabajadorDto;
import com.sena.practica03.dtos.TrabajadorDtoResponse;

public interface TrabajadorServices {

    TrabajadorDtoResponse guardar(TrabajadorDto dto);

    List<TrabajadorDtoResponse> listarTrab();

    TrabajadorDtoResponse trabId(Long id);

    TrabajadorDtoResponse buscarPorDocumento(Byte tipoDocId, String numDoc);

    TrabajadorDtoResponse trabupdate(Long id, TrabajadorDto dto);

    void eliminar(Long id);

}
