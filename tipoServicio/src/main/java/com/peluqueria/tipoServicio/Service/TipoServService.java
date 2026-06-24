package com.peluqueria.tipoServicio.Service;
// Servicio es el encargado de de contener la lógica de negocio y coordinar las operaciones.

import com.peluqueria.tipoServicio.Model.TipoServicio;
import com.peluqueria.tipoServicio.Repository.TipoServRepository;
import com.peluqueria.tipoServicio.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // Genera automáticamente un constructor que incluye el campo final TipoServRepository
public class TipoServService {
    //Al ser final, garantizas que no cambie la variable tipoServRepository una vez ejecutado el código
    private final TipoServRepository tipoServRepository;

    //Mapeo
    private TipoServResponseDTO mapToDTO(TipoServicio tipoServ) {
        return new TipoServResponseDTO(
                tipoServ.getIdTipoServicio(),
                tipoServ.getDescripcionServicio(),
                tipoServ.getPrecioServicio(),
                tipoServ.getDuracionHoras(),
                tipoServ.getEspecialidadAsociada()
        );
    }

    //Obtiene la lista total
    public List<TipoServResponseDTO> obtenerTodas() {
        return tipoServRepository.findAll().stream()
            .map(this::mapToDTO).collect(Collectors.toList());
    }

    //El Optional te avisa si el servicio existe o si el ID enviado no encontró nada (evitando errores de "null")
    public Optional<TipoServResponseDTO> obtenerPorId(Long idTipoServicio) {
        return tipoServRepository.findById(idTipoServicio).map(this::mapToDTO);
    }

    //Recibe un objeto y lo manda a la base de datos. Si el objeto tiene un ID existente, lo actualiza; si no tiene ID, lo crea.
    public TipoServResponseDTO guardar(TipoServRequestDTO dto) {
        TipoServicio tipoServ = new TipoServicio(
            null,
            dto.getDescripcionServicio(), 
            dto.getPrecioServicio(), 
            dto.getDuracionHoras(),
            dto.getEspecialidadAsociada());
        return mapToDTO(tipoServRepository.save(tipoServ));
    }

    public Optional<TipoServResponseDTO> actualizar(Long id, TipoServRequestDTO dto) {
        return tipoServRepository.findById(id).map(existente -> {
            existente.setDescripcionServicio(dto.getDescripcionServicio());
            existente.setPrecioServicio(dto.getPrecioServicio());
            existente.setDuracionHoras(dto.getDuracionHoras());
            existente.setEspecialidadAsociada(dto.getEspecialidadAsociada());
            return mapToDTO(tipoServRepository.save(existente));
        });
    }

    //Borra los datos del ID especificado
    public void eliminar(Long idTipoServicio) {
        tipoServRepository.deleteById(idTipoServicio);
    }    
}
