package com.peluqueria.bodega.Service;
// Servicio es el encargado de de contener la lógica de negocio y coordinar las operaciones.

import com.peluqueria.bodega.Model.Bodega;
import com.peluqueria.bodega.Repository.BodegaRepository;
import com.peluqueria.bodega.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // Genera automáticamente un constructor que incluye el campo final BodegaRepository
public class BodegaService {
    //Al ser final, garantizas que no cambie la variable bodegaRepository una vez ejecutado el código
    private final BodegaRepository bodegaRepository;

    //Mapeo
    private BodegaResponseDTO mapToDTO(Bodega bodega) {
        return new BodegaResponseDTO(
                bodega.getIdBodega(),
                bodega.getDescripcionBodega(),
                bodega.getUbicacionBodega()
        );
    
    }

    //Obtiene la lista total
    public List<BodegaResponseDTO> obtenerTodas() {
        return bodegaRepository.findAll().stream()
            .map(this::mapToDTO).collect(Collectors.toList());
    }

    //El Optional te avisa si el servicio existe o si el ID enviado no encontró nada (evitando errores de "null")
    public Optional<BodegaResponseDTO> obtenerPorId(Long idBodega) {
        return bodegaRepository.findById(idBodega).map(this::mapToDTO);
    }

    //Recibe un objeto y lo manda a la base de datos. Si el objeto tiene un ID existente, lo actualiza; si no tiene ID, lo crea.
    public BodegaResponseDTO guardar(BodegaRequestDTO dto) {
        Bodega bodega = new Bodega(
            null,
            dto.getDescripcionBodega(),
            dto.getUbicacionBodega()
        );
        return mapToDTO(bodegaRepository.save(bodega));
    }

    public Optional<BodegaResponseDTO> actualizar(Long id, BodegaRequestDTO dto) {
        return bodegaRepository.findById(id).map(existente -> {
            existente.setDescripcionBodega(dto.getDescripcionBodega());
            existente.setUbicacionBodega(dto.getUbicacionBodega());
            return mapToDTO(bodegaRepository.save(existente));
        });
    }

    //Borra los datos del ID especificado
    public void eliminar(Long idBodega) {
        bodegaRepository.deleteById(idBodega);
    }    
}
