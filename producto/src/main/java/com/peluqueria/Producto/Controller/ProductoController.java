package com.peluqueria.producto.Controller;

import com.peluqueria.producto.Service.ProductoService;
import com.peluqueria.producto.dto.ProductoRequestDTO;
import com.peluqueria.producto.dto.ProductoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
@Tag(name = "Producto", description = "Operaciones de Producto")
public class ProductoController {
    private final ProductoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los registros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable ("id") Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener por nombre")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@PathVariable ("nombre") String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Crear registro")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro")
    @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable ("id") Long id,
            @Valid @RequestBody ProductoRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro")
    @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<String> eliminar(@PathVariable ("id") Long id) {
        if (service.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("El registro " + id + " fue eliminado correctamente.");
    }
}
