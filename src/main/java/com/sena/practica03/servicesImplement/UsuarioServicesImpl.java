package com.sena.practica03.servicesImplement;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.practica03.dtos.UsuarioDto;
import com.sena.practica03.dtos.UsuarioDtoResponse;
import com.sena.practica03.mappers.UsuarioMapper;
import com.sena.practica03.models.Ciudad;
import com.sena.practica03.models.auth.UsuarioAuth;
import com.sena.practica03.models.usuarios.TipoDocumento;
import com.sena.practica03.models.usuarios.Usuario;
import com.sena.practica03.respositoryes.CiudadRepository;
import com.sena.practica03.respositoryes.TipoDocumentoRepository;
import com.sena.practica03.respositoryes.UsuarioAuthRepository;
import com.sena.practica03.respositoryes.UsuarioRespository;
import com.sena.practica03.services.UsuarioServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicesImpl implements UsuarioServices {

    // Inyección de dependencias mediante constructor, gracias a la anotación
    // @RequiredArgsConstructor de Lombok
    private final UsuarioRespository usuarioRespository;
    private final UsuarioAuthRepository usuarioAuthRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final UsuarioMapper usuarioMapper;
    private final CiudadRepository ciudadRepository;
    /*
     * Inyección de PasswordEncoder para encriptar contraseñas
     * PasswordEncoder es una interfaz de Spring Security que proporciona métodos
     * para encriptar y verificar contraseñas. Se utiliza para proteger las
     * contraseñas de los usuarios antes de almacenarlas en la base de datos.
     * la dependencia necesaria para usar PasswordEncoder se encuentra en el archivo
     * pom.xml, donde se incluye la dependencia de Spring Security. Esta dependencia
     * proporciona la implementación de PasswordEncoder y otras funcionalidades de
     * seguridad.
     * revisar el archivo pom.xml para asegurarse de que la dependencia de Spring
     * Security esté incluida en el proyecto.
     */
    private final PasswordEncoder passwordEncoder;

    // Guardar o crear Usuario
    @Override
    /*
     * Transactional garantiza que todas las operaciones de base de datos dentro de
     * un método se ejecuten como una única unidad de trabajo indivisible (todo se
     * guarda o todo
     * se deshace si algo falla). Si ocurre una excepción que provoque rollback, se
     * revierten los cambios realizados dentro de ella.
     * import org.springframework.transaction.annotation.Transactional;
     */
    @Transactional
    public UsuarioDtoResponse guardar(UsuarioDto usuarioDto) {

        // 1. Validar que el username no exista
        if (usuarioAuthRepository.existsByUser(usuarioDto.getUser())) {
            throw new IllegalArgumentException(
                    "El user de usuario ya se encuentra registrado");
        }

        // 2. Validar que el tipo de documento exista
        TipoDocumento tipoDocumento = tipoDocumentoRepository
                .findById(usuarioDto.getTipoDoc())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tipo de documento no encontrado"));

        // 3. Validar que el documento no esté registrado
        if (usuarioRespository.existsByTipoDocAndNumDoc(
                tipoDocumento, usuarioDto.getNumDoc())) {

            throw new IllegalArgumentException(
                    "El documento ya se encuentra registrado");
        }

        // 4. Validar que la ciudad exista
        Ciudad ciudad = ciudadRepository.findById(usuarioDto.getCiudad())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ciudad no encontrada"));

        // 5. Convertir DTO a entidad Usuario
        Usuario usuario = usuarioMapper.convierteaEntidad(usuarioDto);

        // los datos que no se optinene desde el mapper, se asignan manualmente, como el
        // id, tipo de documento y ciudad.
        usuario.setId(null);
        usuario.setTipoDoc(tipoDocumento);
        usuario.setCiudad(ciudad);

        // 6. Guardar Usuario
        Usuario usuarioGuardado = usuarioRespository.save(usuario);

        // 7. Crear las credenciales de autenticación
        UsuarioAuth usuarioAuth = UsuarioAuth.builder()
                .usuario(usuarioGuardado)
                .user(usuarioDto.getUser())
                // Encriptar la contraseña antes de guardarla
                // uso de passwordEncoder para encriptar la contraseña antes de guardarla en la
                // base de datos. Esto es una buena práctica de seguridad para proteger las
                // contraseñas de los usuarios.
                // mediante metodo encode() de PasswordEncoder, que aplica un algoritmo de
                // hashing a la contraseña proporcionada. El resultado es una versión encriptada
                // de la contraseña que se almacena en la base de datos en lugar de la
                // contraseña en texto plano.

                .pass(passwordEncoder.encode(usuarioDto.getPassword()))
                .habilitado(true)
                .build();

        // 8. Guardar credenciales
        usuarioAuthRepository.save(usuarioAuth);

        // 8. Retornar información del usuario
        return usuarioMapper.convierteaDto(usuarioGuardado);

    }

    // Listar todos los Usuarios
    @Override
    // el readOnly = true indica que la transacción es de solo lectura, lo que
    // significa que no se realizarán cambios en la base de datos durante la
    // ejecución del método. Esto puede mejorar el rendimiento y la eficiencia de la
    // consulta.
    @Transactional(readOnly = true)
    public List<UsuarioDtoResponse> listarTodos() {
        return usuarioRespository.findAll()
                .stream()
                .map(usuarioMapper::convierteaDto)
                .toList();
    }

    // consultar Usuario po Id
    @Override
    @Transactional(readOnly = true)
    public UsuarioDtoResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado" + id));
        return usuarioMapper.convierteaDto(usuario);
    }

    // consultar Usuario por numero de documento
    @Override
    @Transactional(readOnly = true)
    public UsuarioDtoResponse buscarPorNumDoc(String numDoc) {
        Usuario usuario = usuarioRespository.findByNumDoc(numDoc);
        if (usuario == null) {
            throw new EntityNotFoundException(
                    "Usuario con numero de documento: " + numDoc + " no encontrado");
        }
        return usuarioMapper.convierteaDto(usuario);
    }

    // Actualizar usuario
    @Override
    @Transactional
    public UsuarioDtoResponse actualizar(Long id, UsuarioDto usuarioDto) {
        Usuario usuarioEncotrado = usuarioRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro Usuario con Id, " + id));

        // validar ciudad, si no existe, lanzar excepción
        Ciudad ciudad = ciudadRepository.findById(usuarioDto.getCiudad())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ciudad no encontrada"));

        // Actualizar Datos
        usuarioEncotrado.setNom(usuarioDto.getNomb());
        usuarioEncotrado.setApe(usuarioDto.getApell());
        usuarioEncotrado.setDir(usuarioDto.getDirec());
        usuarioEncotrado.setTel(usuarioDto.getTele());
        usuarioEncotrado.setCiudad(ciudad);

        // el numero de documento y el tipo de documento no se actualizan, ya que son
        // datos sensibles y no deberían cambiarse una vez que se han registrado.

        Usuario usuarioActualizado = usuarioRespository.save(usuarioEncotrado);

        return usuarioMapper.convierteaDto(usuarioActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        if (!usuarioRespository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Usuario con :" + id + " No encontrado");
        }
        usuarioRespository.deleteById(id);
    }

}
