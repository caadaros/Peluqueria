package com.peluqueria.disponibilidadProfesional.Controller;

import com.peluqueria.disponibilidadProfesional.Service.DisponibilidadService;
import com.peluqueria.disponibilidadProfesional.dto.DisponibilidadRequestDTO;
import com.peluqueria.disponibilidadProfesional.dto.DisponibilidadResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
public class DisponibilidadController {
    private final DisponibilidadService service;

    @GetMapping
    public List<DisponibilidadResponseDTO> obtenerTodas() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadResponseDTO> obtenerPorId(@PathVariable ("id") Long idDisponibilidad) {
        return service.obtenerPorId(idDisponibilidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/profesional/{rutProfesional}")
    public List<DisponibilidadResponseDTO> porProfesional(@PathVariable ("rutProfesional") String rutProfesional) {
        return service.buscarPorProfesional(rutProfesional);
    }

    // GET /api/mascotas/buscar?especie=golden → búsqueda parcial JPQL
    @GetMapping("/fecha/{fecha}")
    public List<DisponibilidadResponseDTO> PorFecha(@PathVariable ("fecha") String fecha) {
        return service.buscarPorFecha(fecha);
    }

    @GetMapping("/profesional/{rutProfesional}/fechayHora/{fecha}/{hora}")
    public List<DisponibilidadResponseDTO> PorFechayHora(@PathVariable ("rutProfesional") String rutProfesional, @PathVariable ("fecha") String fecha, @PathVariable ("hora") String horaInicio) {
        return service.buscarPorFechayHora(rutProfesional, fecha, horaInicio);
    }

    @PostMapping
    public ResponseEntity<DisponibilidadResponseDTO> crear(@Valid @RequestBody DisponibilidadRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadResponseDTO> actualizar(
            @PathVariable ("id") Long idDisponibilidad,
            @Valid @RequestBody DisponibilidadRequestDTO dto) {
        return service.actualizar(idDisponibilidad, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable ("id") Long idDisponibilidad) {
        if (service.obtenerPorId(idDisponibilidad).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.eliminar(idDisponibilidad);
            return ResponseEntity.ok("La disponibilidad con ID " + idDisponibilidad + " fue eliminada correctamente.");
    }
}
