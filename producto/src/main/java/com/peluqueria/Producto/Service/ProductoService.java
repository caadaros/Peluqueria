package com.peluqueria.producto.Service;
// Servicio es el encargado de de contener la lógica de negocio y coordinar las operaciones.

import com.peluqueria.producto.Model.Producto;
import com.peluqueria.producto.Repository.ProductoRepository;
import com.peluqueria.producto.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // Genera automáticamente un constructor que incluye el campo final ProductoRepository
public class ProductoService {
    //Al ser final, garantizas que no cambie la variable productoRepository una vez ejecutado el código
    private final ProductoRepository productoRepository;

    //Mapeo
    private ProductoResponseDTO mapToDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getDescripcionProducto(),
                producto.getPrecioProducto(),
                producto.getUnidadMedida()
        );
    
    }

    //Obtiene la lista total
    public List<ProductoResponseDTO> obtenerTodas() {
        return productoRepository.findAll().stream()
            .map(this::mapToDTO).collect(Collectors.toList());
    }

    //El Optional te avisa si el servicio existe o si el ID enviado no encontró nada (evitando errores de "null")
    public Optional<ProductoResponseDTO> obtenerPorId(Long idProducto) {
        return productoRepository.findById(idProducto).map(this::mapToDTO);
    }

    //Recibe un objeto y lo manda a la base de datos. Si el objeto tiene un ID existente, lo actualiza; si no tiene ID, lo crea.
    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        Producto producto = new Producto(
            null,
            dto.getDescripcionProducto(), 
            dto.getPrecioProducto(), 
            dto.getUnidadMedida());
        return mapToDTO(productoRepository.save(producto));
    }

    public Optional<ProductoResponseDTO> actualizar(Long id, ProductoRequestDTO dto) {
        return productoRepository.findById(id).map(existente -> {
            existente.setDescripcionProducto(dto.getDescripcionProducto());
            existente.setPrecioProducto(dto.getPrecioProducto());
            existente.setUnidadMedida(dto.getUnidadMedida());
            return mapToDTO(productoRepository.save(existente));
        });
    }

    //Borra los datos del ID especificado
    public void eliminar(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }    
}
