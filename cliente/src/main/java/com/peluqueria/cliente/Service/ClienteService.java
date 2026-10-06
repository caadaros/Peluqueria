package com.peluqueria.cliente.Service;
// Servicio es el encargado de de contener la lógica de negocio y coordinar las operaciones.

import com.peluqueria.cliente.Model.Cliente;
import com.peluqueria.cliente.Repository.ClienteRepository;
import com.peluqueria.cliente.dto.ClienteRequestDTO;
import com.peluqueria.cliente.dto.ClienteResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j //automatiza la creación de un registrador (logger) para manejo de errores
@Service
@RequiredArgsConstructor // Genera automáticamente un constructor que incluye el campo final TipoServRepository
public class ClienteService {
    //Al ser final, garantizas que no cambie la variable tipoServRepository una vez ejecutado el código
    private final ClienteRepository clienteRepository;

    //Mapeo
    private ClienteResponseDTO mapToDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getRutCliente(),
                cliente.getNombreCliente(),
                cliente.getApellidoCliente(),
                cliente.getTelefonoCliente(),
                cliente.getCorreoCliente()
        );
    }

    //Obtiene la lista total
    public List<ClienteResponseDTO> obtenerTodos() {
        return clienteRepository.findAll().stream()
            .map(this::mapToDTO).collect(Collectors.toList());
    }

    //El Optional te avisa si el servicio existe o si el ID enviado no encontró nada (evitando errores de "null")
    public Optional<ClienteResponseDTO> obtenerPorId(String rutCliente) {
        return clienteRepository.findById(rutCliente).map(this::mapToDTO);
    }

    //Recibe un objeto y lo manda a la base de datos. Si el objeto tiene un ID existente, lo actualiza; si no tiene ID, lo crea.
    public ClienteResponseDTO guardar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente(
            dto.getRutCliente(), 
            dto.getNombreCliente(), 
            dto.getApellidoCliente(), 
            dto.getTelefonoCliente(), 
            dto.getCorreoCliente()); 
        return mapToDTO(clienteRepository.save(cliente));
    }

    public Optional<ClienteResponseDTO> actualizar(String rutCliente, ClienteRequestDTO dto) {
        return clienteRepository.findById(rutCliente).map(existente -> {
            existente.setRutCliente(dto.getRutCliente());
            existente.setNombreCliente(dto.getNombreCliente());
            existente.setApellidoCliente(dto.getApellidoCliente());
            existente.setTelefonoCliente(dto.getTelefonoCliente());
            existente.setCorreoCliente(dto.getCorreoCliente());
            return mapToDTO(clienteRepository.save(existente));
        });
    }

    //Borra los datos del ID especificado
    public void eliminar(String rutCliente) {
        clienteRepository.deleteById(rutCliente);
    }    
}
