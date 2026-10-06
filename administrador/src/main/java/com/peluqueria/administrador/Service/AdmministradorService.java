package com.peluqueria.administrador.Service;
// Servicio es el encargado de de contener la lógica de negocio y coordinar las operaciones.

import com.peluqueria.administrador.Model.Administrador;
import com.peluqueria.administrador.Repository.AdministradorRepository;
import com.peluqueria.administrador.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // Genera automáticamente un constructor que incluye el campo final TipoServRepository
public class AdmministradorService {
    //Al ser final, garantizas que no cambie la variable tipoServRepository una vez ejecutado el código
    private final AdministradorRepository administradorRepository;

    //Mapeo
    private AdministradorResponseDTO mapToDTO(Administrador administrador) {
        return new AdministradorResponseDTO(
                administrador.getRutAdministrador(),
                administrador.getNombreAdministrador(),
                administrador.getApellidoAdministrador(),
                administrador.getTelefonoAdministrador(),
                administrador.getCorreoAdministrador(),
                administrador.getPasswordAdministrador(),
                administrador.getEstado()
        );
    }

    //Obtiene la lista total
    public List<AdministradorResponseDTO> obtenerTodas() {
        return administradorRepository.findAll().stream()
            .map(this::mapToDTO).collect(Collectors.toList());
    }

    //El Optional te avisa si el servicio existe o si el ID enviado no encontró nada (evitando errores de "null")
    public Optional<AdministradorResponseDTO> obtenerPorId(String rutAdministrador) {
        return administradorRepository.findById(rutAdministrador).map(this::mapToDTO);
    }

    public List<AdministradorResponseDTO> obtenerPorEstado(String estado) {
        return administradorRepository.findByEstado(estado).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //Recibe un objeto y lo manda a la base de datos. Si el objeto tiene un ID existente, lo actualiza; si no tiene ID, lo crea.
    public AdministradorResponseDTO guardar(AdministradorRequestDTO dto) {
        Administrador administrador = new Administrador(
            dto.getRutAdministrador(), 
            dto.getNombreAdministrador(), 
            dto.getApellidoAdministrador(), 
            dto.getTelefonoAdministrador(), 
            dto.getCorreoAdministrador(),
            dto.getPasswordAdministrador(),
            dto.getEstado()
        ); 
        return mapToDTO(administradorRepository.save(administrador));
    }

    public Optional<AdministradorResponseDTO> actualizar(String rutAdministrador, AdministradorRequestDTO dto) {
        return administradorRepository.findById(rutAdministrador).map(existente -> {
            existente.setRutAdministrador(dto.getRutAdministrador());
            existente.setNombreAdministrador(dto.getNombreAdministrador());
            existente.setApellidoAdministrador(dto.getApellidoAdministrador());
            existente.setTelefonoAdministrador(dto.getTelefonoAdministrador());
            existente.setCorreoAdministrador(dto.getCorreoAdministrador());
            existente.setPasswordAdministrador(dto.getPasswordAdministrador());
            existente.setEstado(dto.getEstado());
            return mapToDTO(administradorRepository.save(existente));
        });
    }


    //Borra los datos del ID especificado
    public void eliminar(String rutAdministrador) {
        administradorRepository.deleteById(rutAdministrador);
    } 
}
