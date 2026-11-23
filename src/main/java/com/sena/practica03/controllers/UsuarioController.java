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
import org.springframework.web.bind.annotation.RestController;

import com.sena.practica03.dtos.UsuarioDto;
import com.sena.practica03.dtos.UsuarioDtoResponse;
import com.sena.practica03.services.UsuarioServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServices usuarioServices;

    // 1 Registrar nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> guardar(@Valid @RequestBody UsuarioDto usuarioDto) {

        UsuarioDtoResponse usuarioguardado = usuarioServices.guardar(usuarioDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioguardado);
    }

    // Listar Usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDtoResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioServices.listarTodos());
    }

    // Consulta usuario por Id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> consutaId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioServices.buscarPorId(id));
    }

    // consulta por post
    @PostMapping("/buscar")
    public ResponseEntity<UsuarioDtoResponse> consutaIdpost(@RequestBody UsuarioDto dto) {
        UsuarioDtoResponse encontrado = usuarioServices.buscarPorId(dto.getId());

        return ResponseEntity.ok(encontrado);
    }

    // Consulta usuario por numero de documento
    @GetMapping("/buscaid")
    public ResponseEntity<UsuarioDtoResponse> consutaNumDoc(@RequestBody UsuarioDto dto) {
        UsuarioDtoResponse encontrado = usuarioServices.buscarPorNumDoc(dto.getNumDoc());

        return ResponseEntity.ok(encontrado);
    }

    // actualizar
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> update(
            @PathVariable Long id,
            @RequestBody UsuarioDto dto) {

        UsuarioDtoResponse usuarioActualizado = usuarioServices.actualizar(
                id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    // actualizar ocultando ID
    @PutMapping("/actualizar")
    public ResponseEntity<UsuarioDtoResponse> update2(
            @RequestBody UsuarioDto dto) {
        Long id = dto.getId();
        UsuarioDtoResponse usuarioActualizado = usuarioServices.actualizar(
                id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar Usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        usuarioServices.eliminar(id);

        return ResponseEntity.ok("Usuario con id :" + id + " Eliminado correctamente");
    }

}
/*
 * @RestController
 * public class UsuarioController {
 * 
 * //inyeccion de dependencias de manera rapida
 * 
 * @Autowired
 * private UsuarioRepository usuarioRepository;
 * 
 * 
 * //metodo para probar el fucniconamiento de la aplicacion
 * 
 * @GetMapping("")
 * public String inicio() {
 * return "Saludo desde el controlador del Usuario controller";
 * }
 * 
 * //Consultar todos los Usuarios de la Base de datos
 * 
 * @GetMapping("/todos")
 * public List<Usuario> AllUsers() {
 * return usuarioRepository.findAll();
 * }
 * 
 * //Consulta usuario por Id- Numero de documento
 * 
 * @GetMapping("/{numdoc}")
 * public ResponseEntity<Usuario> usuarioId(@PathVariable Integer numdoc){
 * return usuarioRepository.findById(numdoc)
 * .map(ResponseEntity::ok)
 * .orElse(ResponseEntity.notFound().build());
 * }
 * 
 * 
 * @PostMapping("/user")
 * public ResponseEntity<Usuario> usuarioporID(@RequestBody Usuario usuario) {
 * return usuarioRepository.findById(usuario.getNumDoc())
 * .map(ResponseEntity::ok)
 * .orElse(ResponseEntity.notFound().build());
 * }
 * //Crear usuario
 * 
 * @PostMapping("/crear")
 * public Usuario crearUsuario(@RequestBody Usuario usuario) {
 * return usuarioRepository.save(usuario);
 * }
 * 
 * //Actualizacion de datos
 * 
 * @PutMapping("/{numDoc}")
 * public ResponseEntity<Usuario> actualizar(@PathVariable Integer
 * numDoc, @RequestBody Usuario updateUser) {
 * return usuarioRepository.findById(numDoc)
 * .map(usuario -> {
 * usuario.setNom(updateUser.getNom());
 * usuario.setApe(updateUser.getApe());
 * usuario.setFecNac(updateUser.getFecNac());
 * return ResponseEntity.ok(usuarioRepository.save(usuario));
 * })
 * .orElse(ResponseEntity.notFound().build());
 * }
 * 
 * 
 * //actualizar por post sin exponer nuemro de documento
 * 
 * @PostMapping("/actualizar")
 * public ResponseEntity<Usuario> actualizarPost(@RequestBody Usuario usuario2)
 * {
 * // obtenemos el numero docuemto desde el Json Recibido
 * Integer numDoc = usuario2.getNumDoc();
 * return usuarioRepository.findById(numDoc)
 * .map(usuario -> {
 * usuario.setNom(usuario2.getNom());
 * usuario.setApe(usuario2.getApe());
 * usuario.setFecNac(usuario2.getFecNac());
 * return ResponseEntity.ok(usuarioRepository.save(usuario));
 * })
 * .orElse(ResponseEntity.notFound().build());
 * }
 * 
 * //elimiar mediante Delete
 * 
 * @DeleteMapping ("/{numDoc}")
 * //para respuesta sin cuerpo
 * //public ResponseEntity<Void> eliminar(@PathVariable Integer numDoc){
 * //respuesa especifica
 * public ResponseEntity<String> eliminar(@PathVariable Integer numDoc){
 * if (usuarioRepository.existsById(numDoc)) {
 * usuarioRepository.deleteById(numDoc);
 * //Devolvemos texto especifico
 * return ResponseEntity.ok("Usuario con documento "+numDoc+" eliminado");
 * }
 * else{
 * return ResponseEntity.status(HttpStatus.NOT_FOUND)
 * .body("Usuario con documento "+numDoc+" NO Existe");
 * }
 * 
 * }
 * 
 * }
 */
