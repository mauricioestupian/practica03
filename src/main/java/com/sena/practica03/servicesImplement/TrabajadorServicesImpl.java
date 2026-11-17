package com.sena.practica03.servicesImplement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.practica03.dtos.TrabajadorDto;
import com.sena.practica03.dtos.TrabajadorDtoResponse;
import com.sena.practica03.mappers.TrabajadorMapper;
import com.sena.practica03.models.usuarios.CargosTrabajador;
import com.sena.practica03.models.usuarios.Trabajador;
import com.sena.practica03.respositoryes.CargoTrabajadorRepository;
import com.sena.practica03.respositoryes.TrabajadorRepository;
import com.sena.practica03.services.TrabajadorServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class TrabajadorServicesImpl implements TrabajadorServices{

    private final TrabajadorRepository traRep;
    private final TrabajadorMapper traMaper;
    private final CargoTrabajadorRepository cargoRepo;

    @Override 
    public TrabajadorDtoResponse guardar(TrabajadorDto dto){

        //1. buscar si cargo existe
        CargosTrabajador cargo = cargoRepo.findById(dto.getCargo())
        .orElseThrow(()-> new EntityNotFoundException
        ("Cargo no encontrado"));

        //2convierte DTO en Entidad
        Trabajador trabajador = traMaper.toEntidad(dto);

        //3.id NULL porque lo genera la base de datos
        trabajador.setId(null);

        //4.  asigna cargo
        trabajador.setCargo(cargo);

        //5 guarda trabajador en Base de Datos
        Trabajador trabajadorguardado = traRep.save(trabajador);

        return traMaper.toDto(trabajadorguardado);

    }

    //Consultar todos los trabajadores
    @Override 
    public List<TrabajadorDtoResponse> listarTrab(){
        return  traRep.findAll()
        .stream()
        .map(traMaper::toDto)
        .toList();
    }

    //TRabajador por Id
    @Override  
    public TrabajadorDtoResponse trabId(Long id){
        Trabajador trabajador = traRep.findById(id)
        .orElseThrow(()-> new EntityNotFoundException(
            "No se encontro trabajador con Id: "+id
        ));

        return traMaper.toDto(trabajador);
     }

     //Actualizar datos Trabajador
     @Override 
     public TrabajadorDtoResponse trabupdate(Long id,TrabajadorDto dto ){
        Trabajador trabExis =  traRep.findById(id)
        .orElseThrow(()-> new EntityNotFoundException(
            "No se encontro trabajador con Id: "+id
        ));
        
        //Actualiza datos heredados de Usuario
        trabExis.setNom(dto.getNomb());
        trabExis.setApe(dto.getApell());
        trabExis.setDir(dto.getDirec());
        trabExis.setTel(dto.getTele());

        //Atributos propios de Trabajador
        trabExis.setCargo(cargoRepo.findById(dto.getCargo())
            .orElseThrow(()-> new EntityNotFoundException(
                "no se encuentra tipo de Trabajador"
            )));

        trabExis.setSueldo(dto.getSueldo());

        Trabajador traActualizado =  traRep.save(trabExis);

        return traMaper.toDto(traActualizado);
     }
     
     //Eliminar Trabajador
     @Override 
     public void eliminar(Long id){
        if (!traRep.existsById(id)) {
            throw new EntityNotFoundException(
                "No se encontro trabajador con Id: "+id
            );
        }

        traRep.deleteById(id);
     }
}
