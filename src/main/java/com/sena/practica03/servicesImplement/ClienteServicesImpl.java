package com.sena.practica03.servicesImplement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.practica03.dtos.ClienteDto;
import com.sena.practica03.dtos.ClienteDtoResponse;
import com.sena.practica03.mappers.ClienteMapper;
import com.sena.practica03.models.usuarios.Cliente;
import com.sena.practica03.models.usuarios.TiposCliente;
import com.sena.practica03.respositoryes.ClienteRepository;
import com.sena.practica03.respositoryes.TipoClienteRepository;
import com.sena.practica03.services.ClienteServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ClienteServicesImpl implements ClienteServices{

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final TipoClienteRepository ticlirepo;

    @Override 
    public ClienteDtoResponse guardar(ClienteDto clienteDto){

        //1. buscar id de tipocliente en base de datos
        TiposCliente tipo = ticlirepo.findById(clienteDto.getTipCli())
        .orElseThrow(()-> new EntityNotFoundException
        ("tipo Cliente no encontrado"));

        //2. Convierte DTO en Entidad
        Cliente cliente = clienteMapper.toEntidad(clienteDto);

        //3.El ID del cliente debe ser nul porque es autoincremental
        cliente.setId(null);

        //4.asigna tipocliente "id"
        cliente.setTipoCliente(tipo);

        //5.guarda cliente en base de datos
        Cliente clienteguardado = clienteRepository.save(cliente);

        return clienteMapper.toDto(clienteguardado);

    }

    //Consultar todos los clientes
    @Override 
    public List<ClienteDtoResponse> listarTodos(){
        return clienteRepository.findAll()
        .stream()
        .map(clienteMapper::toDto)
        .toList();
    }

    //Consulta cliente por ID
    public ClienteDtoResponse buscaCliId(Long id){

        Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            "No se encontro usuario con id: "+id));

            return clienteMapper.toDto(cliente);
    }

    //4 Actualizar Cliente
    @Override 
    public ClienteDtoResponse actualizar(Long id, ClienteDto clienteDto){
        Cliente clienteExistente = clienteRepository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException(
            "No se encontro el cliente con ID: "+id));

            //Artibutos heredadosdel Usuario
            clienteExistente.setNom(clienteDto.getNomb());
            clienteExistente.setApe(clienteDto.getApell());
            clienteExistente.setDir(clienteDto.getDirec());
            clienteExistente.setTel(clienteDto.getTele());

            //Atributos propios del Cliente
            clienteExistente.setTipoCliente(ticlirepo.findById(clienteDto.getTipCli())
                .orElseThrow(()->new EntityNotFoundException(
                    "No se encuentra tipo de cliente "+clienteDto.getTipCli()
                )));
            clienteExistente.setPuntos(clienteDto.getPts());

            Cliente clienteActualizado = clienteRepository.save(clienteExistente);

            return  clienteMapper.toDto(clienteActualizado);
    }

    //Eliminar Cliente
    @Override 
    public void eliminar(Long id){
        if (!clienteRepository.existsById(id)){
            throw new EntityNotFoundException(
                "No se encontro usuario con id: "+id);
        }

        clienteRepository.deleteById(id);
    }

}
