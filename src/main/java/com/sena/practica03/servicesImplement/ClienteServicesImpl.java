package com.sena.practica03.servicesImplement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.practica03.dtos.ClienteDto;
import com.sena.practica03.dtos.ClienteDtoResponse;
import com.sena.practica03.mappers.ClienteMapper;
import com.sena.practica03.models.Ciudad;
import com.sena.practica03.models.auth.Rol;
import com.sena.practica03.models.auth.UsuarioAuth;
import com.sena.practica03.models.auth.UsuarioAuthRol;
import com.sena.practica03.models.usuarios.Cliente;
import com.sena.practica03.models.usuarios.TipoDocumento;
import com.sena.practica03.models.usuarios.TiposCliente;
import com.sena.practica03.respositoryes.CiudadRepository;
import com.sena.practica03.respositoryes.ClienteRepository;
import com.sena.practica03.respositoryes.RolRepository;
import com.sena.practica03.respositoryes.TipoClienteRepository;
import com.sena.practica03.respositoryes.TipoDocumentoRepository;
import com.sena.practica03.respositoryes.UsuarioAuthRepository;
import com.sena.practica03.respositoryes.UsuarioAuthRolRepository;
import com.sena.practica03.respositoryes.UsuarioRespository;
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
        private final UsuarioRespository usuarioRespository;
        private final CiudadRepository ciudadRepository;

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

                // 3. Validar que el documento no esté registrado
                if (usuarioRespository.existsByTipoDocAndNumDoc(
                                tipoDocumento, clienteDto.getNumDoc())) {
                        throw new IllegalArgumentException("El documento ya se encuentra registrado");
                }

                // 4. validar ciudad, si no existe, lanzar excepción
                Ciudad ciudad = ciudadRepository.findById(clienteDto.getCiudad())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Ciudad no encontrada"));

                // 5. Convierte DTO en Entidad
                Cliente cliente = clienteMapper.toEntidad(clienteDto);

                // asigana datos que no se mapena en el mapper
                // El ID del cliente debe ser nul porque es autoincremental
                cliente.setId(null);
                // asigna tipocliente "id"
                cliente.setTipoCliente(tipo);
                // asigna ciudad
                cliente.setCiudad(ciudad);
                // Inicializa los puntos en 0 al crear un nuevo cliente
                cliente.setPuntos(0);
                // asigna tipo de documento
                cliente.setTipoDoc(tipoDocumento);

                // 6.guarda cliente en base de datos
                Cliente clienteGuardado = clienteRepository.save(cliente);

                // 7. Crear cuenta de autenticación
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

                // 8. Buscar el rol de cliente en la base de datos para extraer su ID y
                // asignarlo al usuario creado. Esto asegura que el nuevo cliente tenga los
                // permisos y accesos correspondientes a su rol en el sistema.
                Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Rol de cliente no encontrado"));

                // 9. Registrar asignación del rol
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

        // Consulta cliente por numero de documento y tipo de documento teniendo en que
        // se hereda de
        // Usuario, por lo que se debe buscar en la tabla de Usuario.
        @Override
        @Transactional(readOnly = true)
        public ClienteDtoResponse buscarPorDocumento(
                        Byte tipoDocId, String numDoc) {

                Cliente cliente = clienteRepository
                                .findByTipoDocIdAndNumDoc(numDoc, tipoDocId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Cliente no encontrado con el documento indicado"));

                return clienteMapper.toDto(cliente);
        }

        // Actualizar Cliente
        @Override
        @Transactional
        public ClienteDtoResponse actualizar(Long id, ClienteDto clienteDto) {

                // Validar que el cliente exista en la base de datos antes de intentar
                // actualizarlo. Esto asegura que no se intente modificar un registro
                // inexistente, lo que podría causar errores o inconsistencias en la aplicación.
                Cliente clienteExsistente = clienteRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No se encontro el cliente con ID: " + id));

                // validar ciudad, si no existe, lanzar excepción
                Ciudad ciudad = ciudadRepository.findById(clienteDto.getCiudad())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Ciudad no encontrada"));
                // validar tipo de Cliente, si no existe, lanzar excepción
                TiposCliente tipoCliente = ticlirepo.findById(clienteDto.getTipCli())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Tipo de cliente no encontrado"));

                // Actualizar los atributos del cliente existente con los valores del DTO
                // recibido.
                // Esto permite modificar la información del cliente en la base de datos según
                // los datos proporcionados
                // Artibutos heredadosdel Usuario
                clienteExsistente.setNom(clienteDto.getNomb());
                clienteExsistente.setApe(clienteDto.getApell());
                clienteExsistente.setDir(clienteDto.getDirec());
                clienteExsistente.setTel(clienteDto.getTele());
                clienteExsistente.setCiudad(ciudad);

                // Atributos propios del Cliente
                clienteExsistente.setTipoCliente(tipoCliente);
                // clienteExistente.setPuntos(clienteDto.getPts());
                // Los puntos deben modificarse mediante una operación específica, por ejemplo,
                // cuando el cliente realiza una compra o recibe un servicio que acumula puntos.

                Cliente clienteActualizado = clienteRepository.save(clienteExsistente);

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
