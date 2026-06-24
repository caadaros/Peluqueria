package com.peluqueria.agenda.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.peluqueria.agenda.Service.AgendaService;
import com.peluqueria.agenda.dto.AgendaRequestDTO;
import com.peluqueria.agenda.dto.AgendaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agenda")
@RequiredArgsConstructor
@Validated
public class AgendaController {
    private final AgendaService service;

    @GetMapping
    public List<AgendaResponseDTO> obtenerTodas() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> obtenerPorId(@PathVariable ("id") Integer idAgenda) {
        return service.obtenerPorId(idAgenda)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/profesional/{rutProfesional}")
    public List<AgendaResponseDTO> porProfesional(@PathVariable ("rutProfesional") String rutProfesional) {
        return service.buscarPorProfesional(rutProfesional);
    }

    @GetMapping("/cliente/{rutCliente}")
    public List<AgendaResponseDTO> porCliente(@PathVariable ("rutCliente") String rutCliente) {
        return service.buscarPorCliente(rutCliente);
    }

    // GET /api/mascotas/buscar?especie=golden → búsqueda parcial JPQL
    @GetMapping("/fecha/{fecha}")
    public List<AgendaResponseDTO> PorFecha(@PathVariable ("fecha") String fecha) {
        return service.buscarPorFecha(fecha);
    }

    @PostMapping
    public ResponseEntity<AgendaResponseDTO> crear(@Valid @RequestBody AgendaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> actualizar(
            @PathVariable ("id") Integer idAgenda,
            @Valid @RequestBody AgendaRequestDTO dto) {
        return service.actualizar(idAgenda, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable ("id") Integer idAgenda) {
        if (service.obtenerPorId(idAgenda).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.eliminar(idAgenda);
        return ResponseEntity.noContent().build();
    }
}
