package com.peluqueria.producto.Controller;
//Controlador. Su trabajo es recibir las peticiones que llegan por internet (HTTP) y decidir qué hacer con ellas.

import com.peluqueria.producto.dto.*;
import com.peluqueria.producto.Service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/producto") //URL inicial
@RequiredArgsConstructor
@Tag(name = "Producto", description = "Operaciones relacionadas con el producto")
public class ProductoController {
    //Al ser final, garantizas que no cambie la variable ProductoService una vez ejecutado el código
    private final ProductoService productoService;
    
    //devuelve la lista completa
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista con todos los productos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(productoService.obtenerTodas());
    }

    //Busca por ID, si la encuentra la muestra, de lo contrario irá al GlobalException
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico según su ID")
    @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable ("id")Long idProducto) {
        return productoService.obtenerPorId(idProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Recibe un JSON del cuerpo y valida que los datos estén bien (devuelve un 201) y crea un Producto
    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO producto) {
        return ResponseEntity.status(201).body(productoService.guardar(producto));
    }

    //busca el ID ingresado, si existe sobrescribe el dato anterior, de lo contrario devuelve un NotFound
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente según su ID")
    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable ("id") Long idProducto,
            @Valid @RequestBody ProductoRequestDTO dto) {
        return productoService.actualizar(idProducto, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verifica  si el ID existe, si no existe responde un notFound, si existe lo elimina e indica un mensaje de elimiado
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente según su ID")
    @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable ("id") Long idProducto) {
        if (productoService.obtenerPorId(idProducto).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminar(idProducto);
        return ResponseEntity.noContent().build();
    }
}
