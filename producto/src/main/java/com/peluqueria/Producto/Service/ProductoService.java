package com.peluqueria.producto.Service;

import com.peluqueria.producto.Model.Producto;
import com.peluqueria.producto.Repository.ProductoRepository;
import com.peluqueria.producto.dto.ProductoRequestDTO;
import com.peluqueria.producto.dto.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;

    private ProductoResponseDTO mapToDTO(Producto e) {
        return new ProductoResponseDTO(
                e.getIdProducto(),
                e.getSkuProducto(),
                e.getNombreProducto(),
                e.getDescripcionProducto(),
                e.getPrecioProducto(),
                e.getEstado());
    }

    private void validar(ProductoRequestDTO dto, Long idActual) {
        repository.findBySkuProducto(dto.getSkuProducto()).ifPresent(p -> {
            if (idActual == null || !p.getIdProducto().equals(idActual)) {
                throw new RuntimeException("Ya existe un producto con el SKU " + dto.getSkuProducto());
            }
        });
    }

    public List<ProductoResponseDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ProductoResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return repository.findByNombreProductoContainingIgnoreCase(nombre).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        validar(dto, null);
        Producto nuevo = new Producto(null, dto.getSkuProducto(), dto.getNombreProducto(), dto.getDescripcionProducto(), dto.getPrecioProducto(), dto.getEstado());
        return mapToDTO(repository.save(nuevo));
    }

    public Optional<ProductoResponseDTO> actualizar(Long id, ProductoRequestDTO dto) {
        return repository.findById(id).map(existente -> {
            validar(dto, id);
            existente.setSkuProducto(dto.getSkuProducto());
            existente.setNombreProducto(dto.getNombreProducto());
            existente.setDescripcionProducto(dto.getDescripcionProducto());
            existente.setPrecioProducto(dto.getPrecioProducto());
            existente.setEstado(dto.getEstado());
            return mapToDTO(repository.save(existente));
        });
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
