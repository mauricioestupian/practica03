package com.sena.practica03.servicesImplement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.practica03.dtos.ClienteDto;
import com.sena.practica03.dtos.ClienteDtoResponse;
import com.sena.practica03.mappers.ClienteMapper;
import com.sena.practica03.models.auth.Rol;
import com.sena.practica03.models.auth.UsuarioAuth;
import com.sena.practica03.models.auth.UsuarioAuthRol;
import com.sena.practica03.models.usuarios.Cliente;
import com.sena.practica03.models.usuarios.TipoDocumento;
import com.sena.practica03.models.usuarios.TiposCliente;
import com.sena.practica03.respositoryes.ClienteRepository;
import com.sena.practica03.respositoryes.RolRepository;
import com.sena.practica03.respositoryes.TipoClienteRepository;
import com.sena.practica03.respositoryes.TipoDocumentoRepository;
import com.sena.practica03.respositoryes.UsuarioAuthRepository;
import com.sena.practica03.respositoryes.UsuarioAuthRolRepository;
import com.sena.practica03.services.ClienteServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServicesImpl implements ClienteServices {

        private final ClienteRepository clienteRepository;
        private final UsuarioAuthRepository usuarioAuthRepository;
        private final ClienteMapper clienteMapper;
        private final TipoClienteRepository ticlirepo;
        private final PasswordEncoder passwordEncoder;
        private final UsuarioAuthRolRepository usuarioAuthRolRepository;
        private final RolRepository rolRepository;
        private final TipoDocumentoRepository tipoDocumentoRepository;

        @Override
        // garantiza que todas las operaciones de base de datos dentro de un método se
        // ejecuten como una única unidad de trabajo indivisible (todo se guarda o todo
        // se deshace si algo falla).
        // Si ocurre una excepción que provoque rollback, se revierten los cambios
        // realizados dentro de ella.
        // import org.springframework.transaction.annotation.Transactional;
        @Transactional
        public ClienteDtoResponse guardar(ClienteDto clienteDto) {

                // 1. buscar id de tipocliente en base de datos
                TiposCliente tipo = ticlirepo.findById(clienteDto.getTipCli())
                                .orElseThrow(() -> new EntityNotFoundException("tipo Cliente no encontrado"));

                // 2. Validar que el tipo de documento exista
                TipoDocumento tipoDocumento = tipoDocumentoRepository
                                .findById(clienteDto.getTipoDoc())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Tipo de documento no encontrado"));

                // 2. Convierte DTO en Entidad
                Cliente cliente = clienteMapper.toEntidad(clienteDto);

                // 3.El ID del cliente debe ser nul porque es autoincremental
                cliente.setId(null);

                // 4.asigna tipocliente "id"
                cliente.setTipoCliente(tipo);

                // Inicializa los puntos en 0 al crear un nuevo cliente
                cliente.setPuntos(0);
                cliente.setTipoDoc(tipoDocumento);

                // 5.guarda cliente en base de datos
                Cliente clienteGuardado = clienteRepository.save(cliente);

                // 6. Crear cuenta de autenticación
                // Validar que el nombre de usuario no exista en la base de datos antes de crear
                // la cuenta de autenticación. Esto evita duplicados y asegura que cada cliente
                // tenga un nombre de usuario único para iniciar sesión.
                if (usuarioAuthRepository.existsByUser(clienteDto.getUser())) {
                        throw new IllegalArgumentException(
                                        "El nombre de usuario ya está registrado");
                }
                UsuarioAuth auth = UsuarioAuth.builder()
                                .usuario(clienteGuardado)
                                .user(clienteDto.getUser())
                                .pass(passwordEncoder.encode(clienteDto.getPassword()))
                                .build();

                usuarioAuthRepository.save(auth);

                // 7. Buscar el rol de cliente en la base de datos para extraer su ID y
                // asignarlo al usuario creado. Esto asegura que el nuevo cliente tenga los
                // permisos y accesos correspondientes a su rol en el sistema.
                Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Rol de cliente no encontrado"));

                // 8. Registrar asignación del rol
                UsuarioAuthRol asignacion = UsuarioAuthRol.builder()
                                .usuarioAuth(auth)
                                .rol(rolCliente)
                                .activo(true)
                                .fechaAsignacion(LocalDateTime.now())
                                .observacion("Rol asignado automáticamente al registrar el cliente")
                                .build();

                usuarioAuthRolRepository.save(asignacion);

                return clienteMapper.toDto(clienteGuardado);

        }

        // Consultar todos los clientes
        @Override
        @Transactional(readOnly = true)
        public List<ClienteDtoResponse> listarTodos() {
                return clienteRepository.findAll()
                                .stream()
                                .map(clienteMapper::toDto)
                                .toList();
        }

        // Consulta cliente por ID
        @Override
        @Transactional(readOnly = true)
        public ClienteDtoResponse buscaCliId(Long id) {

                Cliente cliente = clienteRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No se encontro usuario con id: " + id));

                return clienteMapper.toDto(cliente);
        }

        // 4 Actualizar Cliente
        @Override
        @Transactional
        public ClienteDtoResponse actualizar(Long id, ClienteDto clienteDto) {
                Cliente clienteExistente = clienteRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No se encontro el cliente con ID: " + id));

                // Artibutos heredadosdel Usuario
                clienteExistente.setNom(clienteDto.getNomb());
                clienteExistente.setApe(clienteDto.getApell());
                clienteExistente.setDir(clienteDto.getDirec());
                clienteExistente.setTel(clienteDto.getTele());

                // Atributos propios del Cliente
                clienteExistente.setTipoCliente(ticlirepo.findById(clienteDto.getTipCli())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No se encuentra tipo de cliente " + clienteDto.getTipCli())));
                // clienteExistente.setPuntos(clienteDto.getPts());
                // Los puntos deben modificarse mediante una operación específica, por ejemplo,
                // cuando el cliente realiza una compra o recibe un servicio que acumula puntos.

                Cliente clienteActualizado = clienteRepository.save(clienteExistente);

                return clienteMapper.toDto(clienteActualizado);
        }

        // Eliminar Cliente
        @Override
        @Transactional
        public void eliminar(Long id) {
                if (!clienteRepository.existsById(id)) {
                        throw new EntityNotFoundException(
                                        "No se encontro usuario con id: " + id);
                }

                clienteRepository.deleteById(id);
        }

}
