package com.peluqueria.kardex.Controller;

import com.peluqueria.kardex.Service.MovimientoService;
import com.peluqueria.kardex.dto.MovimientoRequestDTO;
import com.peluqueria.kardex.dto.MovimientoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.peluqueria.kardex.dto.StockResponseDTO;
@RestController
@RequestMapping("/api/kardex")
@RequiredArgsConstructor
@Tag(name = "Kardex", description = "Operaciones de Kardex")
public class MovimientoController {
    private final MovimientoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los registros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<MovimientoResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    public ResponseEntity<MovimientoResponseDTO> obtenerPorId(@PathVariable ("id") Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{idProducto}")
    @Operation(summary = "Obtener por producto")
    public ResponseEntity<List<MovimientoResponseDTO>> buscarPorProducto(@PathVariable ("idProducto") Long idProducto) {
        return ResponseEntity.ok(service.buscarPorProducto(idProducto));
    }

    @GetMapping("/bodega/{idBodega}")
    @Operation(summary = "Obtener por bodega")
    public ResponseEntity<List<MovimientoResponseDTO>> buscarPorBodega(@PathVariable ("idBodega") Long idBodega) {
        return ResponseEntity.ok(service.buscarPorBodega(idBodega));
    }

    @PostMapping
    @Operation(summary = "Crear registro")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<MovimientoResponseDTO> crear(@Valid @RequestBody MovimientoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @GetMapping("/stock/{idProducto}/{idBodega}")
    @Operation(summary = "Consultar stock de un producto en una bodega")
    public ResponseEntity<StockResponseDTO> stock(@PathVariable ("idProducto") Long idProducto, @PathVariable ("idBodega") Long idBodega) {
        return ResponseEntity.ok(service.obtenerStock(idProducto, idBodega));
    }
}
