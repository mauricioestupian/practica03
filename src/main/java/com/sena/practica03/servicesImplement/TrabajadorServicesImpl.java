package com.sena.practica03.servicesImplement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.practica03.dtos.TrabajadorDto;
import com.sena.practica03.dtos.TrabajadorDtoResponse;
import com.sena.practica03.mappers.TrabajadorMapper;
import com.sena.practica03.models.auth.Rol;
import com.sena.practica03.models.auth.UsuarioAuth;
import com.sena.practica03.models.auth.UsuarioAuthRol;
import com.sena.practica03.models.usuarios.CargosTrabajador;
import com.sena.practica03.models.usuarios.TipoDocumento;
import com.sena.practica03.models.usuarios.Trabajador;
import com.sena.practica03.respositoryes.CargoTrabajadorRepository;
import com.sena.practica03.respositoryes.TipoDocumentoRepository;
import com.sena.practica03.respositoryes.TrabajadorRepository;
import com.sena.practica03.respositoryes.UsuarioAuthRepository;
import com.sena.practica03.respositoryes.UsuarioAuthRolRepository;
import com.sena.practica03.respositoryes.UsuarioRespository;
import com.sena.practica03.services.TrabajadorServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrabajadorServicesImpl implements TrabajadorServices {

        private final UsuarioRespository usuarioRespository;
        private final TrabajadorRepository traRep;
        private final TrabajadorMapper traMaper;
        private final CargoTrabajadorRepository cargoRepo;

        private final TipoDocumentoRepository tipoDocumentoRepository;
        private final UsuarioAuthRepository usuarioAuthRepository;
        private final UsuarioAuthRolRepository usuarioAuthRolRepository;
        private final PasswordEncoder passwordEncoder;

        // Gaurdar o crear Trabajador
        @Override
        @Transactional
        public TrabajadorDtoResponse guardar(TrabajadorDto dto) {

                // 1. Validar que el usuario no exista
                if (usuarioAuthRepository.existsByUser(dto.getUser())) {
                        throw new IllegalArgumentException(
                                        "El nombre de usuario ya está registrado");
                }

                // 2. Buscar el cargo
                CargosTrabajador cargo = cargoRepo.findById(dto.getCargo())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Cargo no encontrado"));

                // 3. Buscar el tipo de documento
                TipoDocumento tipoDocumento = tipoDocumentoRepository
                                .findById(dto.getTipoDoc())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Tipo de documento no encontrado"));

                // 3. Validar que el documento no esté registrado
                if (usuarioRespository.existsByTipoDocAndNumDoc(
                                tipoDocumento, dto.getNumDoc())) {

                        throw new IllegalArgumentException(
                                        "El documento ya se encuentra registrado");
                }

                // 4. Obtener el rol asociado al cargo
                Rol rol = cargo.getRol();

                if (rol == null) {
                        throw new EntityNotFoundException(
                                        "El cargo no tiene un rol asignado");
                }

                // 5. Convertir DTO en entidad
                Trabajador trabajador = traMaper.toEntidad(dto);

                // 6. Asignar los datos
                trabajador.setId(null);
                trabajador.setCargo(cargo);
                trabajador.setTipoDoc(tipoDocumento);
                trabajador.setSueldo(dto.getSueldo());

                // 7. Guardar trabajador
                Trabajador trabajadorGuardado = traRep.save(trabajador);

                // 8. Crear cuenta de autenticación
                UsuarioAuth auth = UsuarioAuth.builder()
                                .usuario(trabajadorGuardado)
                                .user(dto.getUser())
                                .pass(passwordEncoder.encode(dto.getPassword()))
                                .build();

                usuarioAuthRepository.save(auth);

                // 9. Asignar automáticamente el rol correspondiente
                UsuarioAuthRol asignacion = UsuarioAuthRol.builder()
                                .usuarioAuth(auth)
                                .rol(rol)
                                .activo(true)
                                .fechaAsignacion(LocalDateTime.now())
                                .observacion("Rol asignado automáticamente según el cargo")
                                .build();

                usuarioAuthRolRepository.save(asignacion);

                // 10. Retornar los datos del trabajador
                return traMaper.toDto(trabajadorGuardado);
        }

        // Consultar todos los trabajadores
        @Override
        @Transactional(readOnly = true)
        public List<TrabajadorDtoResponse> listarTrab() {
                return traRep.findAll()
                                .stream()
                                .map(traMaper::toDto)
                                .toList();
        }

        // TRabajador por Id
        @Override
        @Transactional(readOnly = true)
        public TrabajadorDtoResponse trabId(Long id) {
                Trabajador trabajador = traRep.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No se encontro trabajador con Id: " + id));

                return traMaper.toDto(trabajador);
        }

        // Consultar trabajador por numero de documento y tipo de documento
        @Override
        @Transactional(readOnly = true)
        public TrabajadorDtoResponse buscarPorDocumento(Byte tipoDocId, String numDoc) {
                Trabajador trabajador = traRep.findByTipoDoc_IdAndNumDoc(tipoDocId, numDoc);

                if (trabajador == null) {
                        throw new EntityNotFoundException(
                                        "No se encontro trabajador con Tipo de Documento: " + tipoDocId +
                                                        " y Numero de Documento: " + numDoc);
                }

                return traMaper.toDto(trabajador);
        }

        // Actualizar datos Trabajador
        @Override
        @Transactional
        public TrabajadorDtoResponse trabupdate(Long id, TrabajadorDto dto) {
                Trabajador trabExis = traRep.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "No se encontro trabajador con Id: " + id));

                // Actualiza datos heredados de Usuario
                trabExis.setNom(dto.getNomb());
                trabExis.setApe(dto.getApell());
                trabExis.setDir(dto.getDirec());
                trabExis.setTel(dto.getTele());

                // Atributos propios de Trabajador
                trabExis.setCargo(cargoRepo.findById(dto.getCargo())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "no se encuentra tipo de Trabajador")));

                trabExis.setSueldo(dto.getSueldo());

                Trabajador traActualizado = traRep.save(trabExis);

                return traMaper.toDto(traActualizado);
        }

        // Eliminar Trabajador
        @Override
        @Transactional
        public void eliminar(Long id) {
                if (!traRep.existsById(id)) {
                        throw new EntityNotFoundException(
                                        "No se encontro trabajador con Id: " + id);
                }

                traRep.deleteById(id);
        }
}
