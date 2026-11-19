package com.sena.practica03.servicesImplement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.practica03.dtos.UsuarioDto;
import com.sena.practica03.mappers.UsuarioMapper;
import com.sena.practica03.models.usuarios.Usuario;
import com.sena.practica03.respositoryes.UsuarioRespository;
import com.sena.practica03.services.UsuarioServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicesImpl implements UsuarioServices {

    private final UsuarioRespository usuarioRespository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDto guardar(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.convierteaEntidad(usuarioDto);

        usuario.setId(null);

        Usuario usuarioGuardado = usuarioRespository.save(usuario);

        return usuarioMapper.convierteaDto(usuarioGuardado);
    }

    // Listar todos los Usuarios
    @Override
    public List<UsuarioDto> listarTodos() {
        return usuarioRespository.findAll()
                .stream()
                .map(usuarioMapper::convierteaDto)
                .toList();
    }

    // consultar Usuario po Id
    @Override
    public UsuarioDto buscarPorId(Long id) {
        Usuario usuario = usuarioRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado" + id));
        return usuarioMapper.convierteaDto(usuario);
    }

    // Actualizar usuario
    @Override
    public UsuarioDto actualizar(Long id, UsuarioDto usuarioDto) {
        Usuario usuarioEncotrado = usuarioRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro Usuario con Id, " + id));

        // Actualizar Datos
        usuarioEncotrado.setNom(usuarioDto.getNomb());
        usuarioEncotrado.setApe(usuarioDto.getApell());
        usuarioEncotrado.setDir(usuarioDto.getDirec());
        usuarioEncotrado.setTel(usuarioDto.getTele());

        Usuario usuarioActualizado = usuarioRespository.save(usuarioEncotrado);

        return usuarioMapper.convierteaDto(usuarioActualizado);
    }

    @Override
    public void eliminar(Long id) {

        if (!usuarioRespository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Usuario con :" + id + " No encontrado");
        }
        usuarioRespository.deleteById(id);
    }

}
