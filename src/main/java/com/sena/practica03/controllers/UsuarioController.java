package com.sena.practica03.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.practica03.dtos.UsuarioDto;
import com.sena.practica03.services.UsuarioServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController 
@RequestMapping ("api/usuarios")
@RequiredArgsConstructor 
public class UsuarioController {

    private final UsuarioServices usuarioServices;

    //1 Registrar nuevo usuario
    @PostMapping
    public ResponseEntity <UsuarioDto> guardar(@Valid @RequestBody UsuarioDto usuarioDto) {
        
        UsuarioDto usuarioguardado = usuarioServices.guardar(usuarioDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(usuarioguardado);
    }

    //Listar Usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarTodos() {
        return ResponseEntity.ok(usuarioServices.listarTodos());
    }

    //Consulta usuario por Id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> consutaId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioServices.buscarPorId(id));
    }

    //consulta  por post
    @PostMapping("/buscar")
    public ResponseEntity<UsuarioDto> consutaIdpost(@RequestBody UsuarioDto dto) {
        UsuarioDto encontrado = usuarioServices.buscarPorId(dto.getId());
        
        return ResponseEntity.ok(encontrado);
    }
    
    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(
        @PathVariable Long id, 
        @RequestBody UsuarioDto dto) {
        
            UsuarioDto usuarioActualizado = usuarioServices.actualizar(
                id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }
    

    //actualizar ocultando ID
    @PutMapping("/actualizar")
    public ResponseEntity<UsuarioDto> update2(
        @RequestBody UsuarioDto dto) {
            Long id = dto.getId();
            UsuarioDto usuarioActualizado = usuarioServices.actualizar(
                id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    //Eliminar Usuario
    @DeleteMapping ("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){

        usuarioServices.eliminar(id);

        return ResponseEntity.ok("Usuario con id :"+id+" Eliminado correctamente");
    }


    
    

}
