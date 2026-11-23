package com.sena.practica03.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sena.practica03.dtos.ClienteDto;
import com.sena.practica03.dtos.ClienteDtoResponse;
import com.sena.practica03.services.ClienteServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteServices cliser;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<ClienteDtoResponse> saveCli(@Valid @RequestBody ClienteDto dto) {

        ClienteDtoResponse clisave = cliser.guardar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clisave);
    }

    // Consulta todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDtoResponse>> todosCli() {
        return ResponseEntity.ok(cliser.listarTodos());
    }

    // Consultar Cliente por Id por url
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDtoResponse> cliporId(@RequestParam Long id) {
        return ResponseEntity.ok(cliser.buscaCliId(id));
    }

    // Consultar Cliente por Id oculto
    @PostMapping("/buscar")
    public ResponseEntity<ClienteDtoResponse> cliporId2(@RequestBody ClienteDto dto) {
        return ResponseEntity.ok(cliser.buscaCliId(dto.getId()));
    }

    // Consultar Cliente por Documento
    @GetMapping("/buscar")
    public ResponseEntity<ClienteDtoResponse> cliporDoc(@RequestParam Byte tipoDocId, @RequestParam String numDoc) {
        return ResponseEntity.ok(cliser.buscarPorDocumento(tipoDocId, numDoc));
    }

    // Actualizar Cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDtoResponse> upateCli(@Valid @PathVariable Long id, @RequestBody ClienteDto dto) {

        ClienteDtoResponse clienteActualizado = cliser.actualizar(id, dto);

        return ResponseEntity.ok(clienteActualizado);
    }

    // Eliminar Cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        cliser.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
