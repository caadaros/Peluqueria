package com.peluqueria.notificacion.Controller;

import com.peluqueria.notificacion.Service.NotificacionService;
import com.peluqueria.notificacion.dto.NotificacionRequestDTO;
import com.peluqueria.notificacion.dto.NotificacionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
@RequiredArgsConstructor
@Tag(name = "Notificacion", description = "Operaciones de Notificacion")
public class NotificacionController {
    private final NotificacionService service;

    @GetMapping
    @Operation(summary = "Obtener todos los registros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<NotificacionResponseDTO> obtenerPorId(@PathVariable ("id") Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{rutCliente}")
    @Operation(summary = "Obtener por cliente")
    public ResponseEntity<List<NotificacionResponseDTO>> buscarPorCliente(@PathVariable ("rutCliente") String rutCliente) {
        return ResponseEntity.ok(service.buscarPorCliente(rutCliente));
    }

    @PostMapping
    @Operation(summary = "Crear registro")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<NotificacionResponseDTO> crear(@Valid @RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }
}
