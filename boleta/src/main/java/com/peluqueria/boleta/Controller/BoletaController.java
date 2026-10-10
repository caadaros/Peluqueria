package com.peluqueria.boleta.Controller;

import com.peluqueria.boleta.Service.BoletaService;
import com.peluqueria.boleta.dto.BoletaRequestDTO;
import com.peluqueria.boleta.dto.BoletaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.peluqueria.boleta.dto.EstadoBoletaRequestDTO;
@RestController
@RequestMapping("/api/boleta")
@RequiredArgsConstructor
@Tag(name = "Boleta", description = "Operaciones de Boleta")
public class BoletaController {
    private final BoletaService service;

    @GetMapping
    @Operation(summary = "Obtener todos los registros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<BoletaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<BoletaResponseDTO> obtenerPorId(@PathVariable ("id") Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{rutCliente}")
    @Operation(summary = "Obtener por cliente")
    public ResponseEntity<List<BoletaResponseDTO>> buscarPorCliente(@PathVariable ("rutCliente") String rutCliente) {
        return ResponseEntity.ok(service.buscarPorCliente(rutCliente));
    }

    @PostMapping
    @Operation(summary = "Crear registro")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<BoletaResponseDTO> crear(@Valid @RequestBody BoletaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar el estado de la boleta (Pagada o Anulada)")
    @ApiResponse(responseCode = "200", description = "Estado actualizado")
    @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    public ResponseEntity<BoletaResponseDTO> cambiarEstado(@PathVariable ("id") Long id, @Valid @RequestBody EstadoBoletaRequestDTO dto) {
        return service.cambiarEstado(id, dto.getEstadoBoleta())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
