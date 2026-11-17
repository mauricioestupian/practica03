package com.sena.practica03.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.practica03.dtos.TrabajadorDto;
import com.sena.practica03.dtos.TrabajadorDtoResponse;
import com.sena.practica03.services.TrabajadorServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController 
@RequestMapping ("api/trabajador")
@RequiredArgsConstructor 
public class TrabajadorController {


    private final TrabajadorServices traSer;

    //Crear
    @PostMapping("/crear")
    public ResponseEntity<TrabajadorDtoResponse> saveTrab(@Valid @RequestBody  TrabajadorDto dto) {
        
        TrabajadorDtoResponse trab = traSer.guardar(dto);
                
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(trab);
    }

    //Consultar todos los trabajadores
    @GetMapping("/todos")
    public ResponseEntity<List<TrabajadorDtoResponse>> todosTrab() {
        return ResponseEntity.ok(traSer.listarTrab());
    }
    
    //Consulta Trabajador por id
    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorDtoResponse> trabId(@PathVariable Long id){
        return ResponseEntity.ok(traSer.trabId(id));
    }

    //Consulta Trabajador por id ocultado 
    @GetMapping("/trab")
    public ResponseEntity<TrabajadorDtoResponse> trabId(@RequestBody TrabajadorDto dto){
        Long id = dto.getId();
        return ResponseEntity.ok(traSer.trabId(id));
    }

    //Actualiza Trabajador por el Id
    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorDtoResponse> actualizaTrab(@Valid @PathVariable Long id,@RequestBody TrabajadorDto dto ) {
        
        TrabajadorDtoResponse trabactualizado = traSer.trabupdate(id, dto);
        
        return ResponseEntity.ok(trabactualizado);
    }

    //Actualiza Trabajador por el Id
    //Ocultando Id
    @PutMapping("/trab")
    public ResponseEntity<TrabajadorDtoResponse> actualizaTrab(@Valid @RequestBody TrabajadorDto dto ) {
        
        Long id = dto.getId();

        TrabajadorDtoResponse trabactualizado = traSer.trabupdate(id, dto);
        
        return ResponseEntity.ok(trabactualizado);
    }
    
    //Eliminar Trabajador
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarTrabajador(@PathVariable("id") Long id) { 
        traSer.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario Eliminado"));
}
}
