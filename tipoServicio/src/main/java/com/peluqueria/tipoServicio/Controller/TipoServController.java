package com.peluqueria.tipoServicio.Controller;
//Controlador. Su trabajo es recibir las peticiones que llegan por internet (HTTP) y decidir qué hacer con ellas.

import com.peluqueria.tipoServicio.dto.*;
import com.peluqueria.tipoServicio.Service.TipoServService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tipoServicio") //URL inicial
@RequiredArgsConstructor
@Tag(name = "Tipo de Servicio", description = "Operaciones relacionadas con el tipo de servicio")
public class TipoServController {
    //Al ser final, garantizas que no cambie la variable TipoServService una vez ejecutado el código
    private final TipoServService tipoServ;
    
    //devuelve la lista completa
    @GetMapping
    @Operation(summary = "Obtener todos los tipos de servicio", description = "Devuelve una lista con todos los tipos de servicio registrados")
    @ApiResponse(responseCode = "200", description = "Lista de tipos de servicio obtenida correctamente")
    public ResponseEntity<List<TipoServResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(tipoServ.obtenerTodas());
    }

    //Busca por ID, si la encuentra la muestra, de lo contrario irá al GlobalException
    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de servicio por ID", description = "Devuelve un tipo de servicio específico según su ID")
    @ApiResponse(responseCode = "200", description = "Tipo de servicio obtenido correctamente")
    @ApiResponse(responseCode = "404", description = "Tipo de servicio no encontrado")
    public ResponseEntity<TipoServResponseDTO> obtenerPorId(@PathVariable ("id")Long idTipoServicio) {
        return tipoServ.obtenerPorId(idTipoServicio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Recibe un JSON del cuerpo y valida que los datos estén bien (devuelve un 201) y crea un TipoServ
    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de servicio", description = "Crea un nuevo tipo de servicio con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Tipo de servicio creado correctamente")
    public ResponseEntity<TipoServResponseDTO> crear(@Valid @RequestBody TipoServRequestDTO TipoServ) {
        return ResponseEntity.status(201).body(tipoServ.guardar(TipoServ));
    }

    //busca el ID ingresado, si existe sobrescribe el dato anterior, de lo contrario devuelve un NotFound
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de servicio", description = "Actualiza un tipo de servicio existente según su ID")
    @ApiResponse(responseCode = "200", description = "Tipo de servicio actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Tipo de servicio no encontrado")
    public ResponseEntity<TipoServResponseDTO> actualizar(
            @PathVariable ("id") Long idTipoServicio,
            @Valid @RequestBody TipoServRequestDTO dto) {
        return tipoServ.actualizar(idTipoServicio, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verifica  si el ID existe, si no existe responde un notFound, si existe lo elimina e indica un mensaje de elimiado
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de servicio", description = "Elimina un tipo de servicio existente según su ID")
    @ApiResponse(responseCode = "200", description = "Tipo de servicio eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Tipo de servicio no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable ("id") Long idTipoServicio) {
        if (tipoServ.obtenerPorId(idTipoServicio).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tipoServ.eliminar(idTipoServicio);
        return ResponseEntity.noContent().build();
    }
}
