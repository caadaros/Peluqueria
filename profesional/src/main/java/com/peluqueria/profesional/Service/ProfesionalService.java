package com.peluqueria.profesional.Service;
// Servicio es el encargado de de contener la lógica de negocio y coordinar las operaciones.

import com.peluqueria.profesional.Model.Profesional;
import com.peluqueria.profesional.Repository.ProfesionalRepository;
import com.peluqueria.profesional.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // Genera automáticamente un constructor que incluye el campo final TipoServRepository
public class ProfesionalService {
    //Al ser final, garantizas que no cambie la variable tipoServRepository una vez ejecutado el código
    private final ProfesionalRepository profesionalRepository;

    //Mapeo
    private ProfesionalResponseDTO mapToDTO(Profesional profesional) {
        return new ProfesionalResponseDTO(
                profesional.getRutProfesional(),
                profesional.getNombreProfesional(),
                profesional.getApellidoProfesional(),
                profesional.getTelefonoProfesional(),
                profesional.getCorreoProfesional(),
                profesional.getEspecialidad(),
                profesional.getEstado()
        );
    }

    //Obtiene la lista total
    public List<ProfesionalResponseDTO> obtenerTodas() {
        return profesionalRepository.findAll().stream()
            .map(this::mapToDTO).collect(Collectors.toList());
    }

    //El Optional te avisa si el servicio existe o si el ID enviado no encontró nada (evitando errores de "null")
    public Optional<ProfesionalResponseDTO> obtenerPorId(String rutProfesional) {
        return profesionalRepository.findById(rutProfesional).map(this::mapToDTO);
    }

    public List<ProfesionalResponseDTO> obtenerPorEspecialidad(String especialidad) {
        return profesionalRepository.findByEspecialidad(especialidad).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProfesionalResponseDTO> obtenerPorEstado(String estado) {
        return profesionalRepository.findByEstado(estado).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //Recibe un objeto y lo manda a la base de datos. Si el objeto tiene un ID existente, lo actualiza; si no tiene ID, lo crea.
    public ProfesionalResponseDTO guardar(ProfesionalRequestDTO dto) {
        Profesional profesional = new Profesional(
            dto.getRutProfesional(), 
            dto.getNombreProfesional(), 
            dto.getApellidoProfesional(), 
            dto.getTelefonoProfesional(), 
            dto.getCorreoProfesional(),
            dto.getEspecialidad(),
            dto.getEstado()
        ); 
        return mapToDTO(profesionalRepository.save(profesional));
    }

    public Optional<ProfesionalResponseDTO> actualizar(String rutProfesional, ProfesionalRequestDTO dto) {
        return profesionalRepository.findById(rutProfesional).map(existente -> {
            existente.setRutProfesional(dto.getRutProfesional());
            existente.setNombreProfesional(dto.getNombreProfesional());
            existente.setApellidoProfesional(dto.getApellidoProfesional());
            existente.setTelefonoProfesional(dto.getTelefonoProfesional());
            existente.setCorreoProfesional(dto.getCorreoProfesional());
            existente.setEspecialidad(dto.getEspecialidad());
            return mapToDTO(profesionalRepository.save(existente));
        });
    }


    //Borra los datos del ID especificado
    public void eliminar(String rutProfesional) {
        profesionalRepository.deleteById(rutProfesional);
    } 
}
