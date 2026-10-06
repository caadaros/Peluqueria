package com.peluqueria.disponibilidadProfesional.Controller;

import com.peluqueria.disponibilidadProfesional.Service.DisponibilidadService;
import com.peluqueria.disponibilidadProfesional.dto.DisponibilidadRequestDTO;
import com.peluqueria.disponibilidadProfesional.dto.DisponibilidadResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
@Tag(name = "Disponibilidad", description = "Controlador para la gestión de disponibilidad de profesionales")
public class DisponibilidadController {
    private final DisponibilidadService service;

    @GetMapping
    @Operation(summary = "Obtener todas las disponibilidades", description = "Recupera una lista de todas las disponibilidades")
    @ApiResponse(responseCode = "200", description = "Lista de disponibilidades obtenida correctamente")
    public ResponseEntity<List<DisponibilidadResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener disponibilidad por ID", description = "Recupera una disponibilidad específica por su ID")
    @ApiResponse(responseCode = "200", description = "Disponibilidad obtenida correctamente")
    @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
    public ResponseEntity<DisponibilidadResponseDTO> obtenerPorId(@PathVariable ("id") Long idDisponibilidad) {
        return service.obtenerPorId(idDisponibilidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/profesional/{rutProfesional}")
    @Operation(summary = "Obtener disponibilidades por profesional", description = "Recupera una lista de disponibilidades para un profesional específico")
    @ApiResponse(responseCode = "200", description = "Lista de disponibilidades obtenida correctamente")
    @ApiResponse(responseCode = "404", description = "Profesional no encontrado")
    public ResponseEntity<List<DisponibilidadResponseDTO>> porProfesional(@PathVariable ("rutProfesional") String rutProfesional) {
        return ResponseEntity.ok(service.buscarPorProfesional(rutProfesional));
    }


    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Obtener disponibilidades por fecha", description = "Recupera una lista de disponibilidades para una fecha específica")
    @ApiResponse(responseCode = "200", description = "Lista de disponibilidades obtenida correctamente")
    @ApiResponse(responseCode = "404", description = "Fecha no encontrada")
    public ResponseEntity<List<DisponibilidadResponseDTO>> porFecha(@PathVariable ("fecha") String fecha) {
        return ResponseEntity.ok(service.buscarPorFecha(fecha));
    }

    @GetMapping("/profesional/{rutProfesional}/fechayHora/{fecha}/{hora}")
    @Operation(summary = "Obtener disponibilidad por profesional y fecha/hora", description = "Recupera una lista de disponibilidades para un profesional específico en una fecha y hora determinadas")
    @ApiResponse(responseCode = "200", description = "Lista de disponibilidades obtenida correctamente")
    @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
    public ResponseEntity<List<DisponibilidadResponseDTO>> porFechayHora(@PathVariable ("rutProfesional") String rutProfesional, @PathVariable ("fecha") String fecha, @PathVariable ("hora") String horaInicio) {
        return ResponseEntity.ok(service.buscarPorFechayHora(rutProfesional, fecha, horaInicio));
    }

    @PostMapping
    @Operation(summary = "Crear disponibilidad", description = "Crea una nueva disponibilidad para un profesional")
    @ApiResponse(responseCode = "201", description = "Disponibilidad creada correctamente")
    public ResponseEntity<DisponibilidadResponseDTO> crear(@Valid @RequestBody DisponibilidadRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar disponibilidad", description = "Actualiza una disponibilidad existente para un profesional")
    @ApiResponse(responseCode = "200", description = "Disponibilidad actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
    public ResponseEntity<DisponibilidadResponseDTO> actualizar(
            @PathVariable ("id") Long idDisponibilidad,
            @Valid @RequestBody DisponibilidadRequestDTO dto) {
        return service.actualizar(idDisponibilidad, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar disponibilidad", description = "Elimina una disponibilidad existente para un profesional")
    @ApiResponse(responseCode = "200", description = "Disponibilidad eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
    public ResponseEntity<String> eliminar(@PathVariable ("id") Long idDisponibilidad) {
        if (service.obtenerPorId(idDisponibilidad).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.eliminar(idDisponibilidad);
            return ResponseEntity.ok("La disponibilidad con ID " + idDisponibilidad + " fue eliminada correctamente.");
    }
}
