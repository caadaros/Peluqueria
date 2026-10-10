package com.peluqueria.registro.Controller;

import com.peluqueria.registro.Service.RegistroService;
import com.peluqueria.registro.dto.RegistroRequestDTO;
import com.peluqueria.registro.dto.RegistroResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/registro")
@RequiredArgsConstructor
@Tag(name = "Registro", description = "Operaciones de Registro")
public class RegistroController {
    private final RegistroService service;

    @GetMapping
    @Operation(summary = "Obtener todos los registros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<RegistroResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<RegistroResponseDTO> obtenerPorId(@PathVariable ("id") Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{rutCliente}")
    @Operation(summary = "Obtener por cliente")
    public ResponseEntity<List<RegistroResponseDTO>> buscarPorCliente(@PathVariable ("rutCliente") String rutCliente) {
        return ResponseEntity.ok(service.buscarPorCliente(rutCliente));
    }

    @GetMapping("/profesional/{rutProfesional}")
    @Operation(summary = "Obtener por profesional")
    public ResponseEntity<List<RegistroResponseDTO>> buscarPorProfesional(@PathVariable ("rutProfesional") String rutProfesional) {
        return ResponseEntity.ok(service.buscarPorProfesional(rutProfesional));
    }

    @PostMapping
    @Operation(summary = "Crear registro")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<RegistroResponseDTO> crear(@Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro")
    @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<RegistroResponseDTO> actualizar(
            @PathVariable ("id") Long id,
            @Valid @RequestBody RegistroRequestDTO dto) {
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
