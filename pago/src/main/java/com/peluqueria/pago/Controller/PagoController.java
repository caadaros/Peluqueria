package com.peluqueria.pago.Controller;

import com.peluqueria.pago.Service.PagoService;
import com.peluqueria.pago.dto.PagoRequestDTO;
import com.peluqueria.pago.dto.PagoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pago")
@RequiredArgsConstructor
@Tag(name = "Pago", description = "Operaciones de Pago")
public class PagoController {
    private final PagoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los registros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable ("id") Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/boleta/{idBoleta}")
    @Operation(summary = "Obtener por boleta")
    public ResponseEntity<List<PagoResponseDTO>> buscarPorBoleta(@PathVariable ("idBoleta") Long idBoleta) {
        return ResponseEntity.ok(service.buscarPorBoleta(idBoleta));
    }

    @GetMapping("/cliente/{rutCliente}")
    @Operation(summary = "Obtener por cliente")
    public ResponseEntity<List<PagoResponseDTO>> buscarPorCliente(@PathVariable ("rutCliente") String rutCliente) {
        return ResponseEntity.ok(service.buscarPorCliente(rutCliente));
    }

    @PostMapping
    @Operation(summary = "Crear registro")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<PagoResponseDTO> crear(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }
}
