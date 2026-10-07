package com.peluqueria.bodega.Controller;
//Controlador. Su trabajo es recibir las peticiones que llegan por internet (HTTP) y decidir qué hacer con ellas.

import com.peluqueria.bodega.dto.*;
import com.peluqueria.bodega.Service.BodegaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/bodega") //URL inicial
@RequiredArgsConstructor
@Tag(name = "Bodega", description = "Operaciones relacionadas con la bodega")
public class BodegaController {
    //Al ser final, garantizas que no cambie la variable BodegaService una vez ejecutado el código
    private final BodegaService bodegaService;
    
    //devuelve la lista completa
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista con todos los productos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    public ResponseEntity<List<BodegaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(bodegaService.obtenerTodas());
    }

    //Busca por ID, si la encuentra la muestra, de lo contrario irá al GlobalException
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico según su ID")
    @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<BodegaResponseDTO> obtenerPorId(@PathVariable ("id")Long idBodega) {
        return bodegaService.obtenerPorId(idBodega)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Recibe un JSON del cuerpo y valida que los datos estén bien (devuelve un 201) y crea un Producto
    @PostMapping
    @Operation(summary = "Crear una nueva bodega", description = "Crea una nueva bodega con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Bodega creada correctamente")
    public ResponseEntity<BodegaResponseDTO> crear(@Valid @RequestBody BodegaRequestDTO bodega) {
        return ResponseEntity.status(201).body(bodegaService.guardar(bodega));
    }

    //busca el ID ingresado, si existe sobrescribe el dato anterior, de lo contrario devuelve un NotFound
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar bodega", description = "Actualiza una bodega existente según su ID")
    @ApiResponse(responseCode = "200", description = "Bodega actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    public ResponseEntity<BodegaResponseDTO> actualizar(
            @PathVariable ("id") Long idBodega,
            @Valid @RequestBody BodegaRequestDTO dto) {
        return bodegaService.actualizar(idBodega, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verifica  si el ID existe, si no existe responde un notFound, si existe lo elimina e indica un mensaje de elimiado
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar bodega", description = "Elimina una bodega existente según su ID")
    @ApiResponse(responseCode = "200", description = "Bodega eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Bodega no encontrada")
    public ResponseEntity<Void> eliminar(@PathVariable ("id") Long idBodega) {
        if (productoService.obtenerPorId(idProducto).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminar(idProducto);
        return ResponseEntity.noContent().build();
    }
}
