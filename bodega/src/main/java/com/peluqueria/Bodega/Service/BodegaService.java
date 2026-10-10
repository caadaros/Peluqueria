package com.peluqueria.bodega.Service;

import com.peluqueria.bodega.Model.Bodega;
import com.peluqueria.bodega.Repository.BodegaRepository;
import com.peluqueria.bodega.dto.BodegaRequestDTO;
import com.peluqueria.bodega.dto.BodegaResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BodegaService {
    private final BodegaRepository repository;

    private BodegaResponseDTO mapToDTO(Bodega e) {
        return new BodegaResponseDTO(
                e.getIdBodega(),
                e.getNombreBodega(),
                e.getDireccionBodega(),
                e.getEstado());
    }

    private void validar(BodegaRequestDTO dto, Long idActual) {
    }

    public List<BodegaResponseDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<BodegaResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public List<BodegaResponseDTO> buscarPorNombre(String nombre) {
        return repository.findByNombreBodegaContainingIgnoreCase(nombre).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public BodegaResponseDTO guardar(BodegaRequestDTO dto) {
        validar(dto, null);
        Bodega nuevo = new Bodega(null, dto.getNombreBodega(), dto.getDireccionBodega(), dto.getEstado());
        return mapToDTO(repository.save(nuevo));
    }

    public Optional<BodegaResponseDTO> actualizar(Long id, BodegaRequestDTO dto) {
        return repository.findById(id).map(existente -> {
            validar(dto, id);
            existente.setNombreBodega(dto.getNombreBodega());
            existente.setDireccionBodega(dto.getDireccionBodega());
            existente.setEstado(dto.getEstado());
            return mapToDTO(repository.save(existente));
        });
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
