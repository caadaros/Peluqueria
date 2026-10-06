package com.peluqueria.administrador.Controller;
//Controlador. Su trabajo es recibir las peticiones que llegan por internet (HTTP) y decidir qué hacer con ellas.

import com.peluqueria.administrador.dto.*;
import com.peluqueria.administrador.Service.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/administrador") //URL inicial
@RequiredArgsConstructor
@Tag(name = "Administrador", description = "Operaciones relacionadas con administradores")
public class AdministradorController {
    //Al ser final, garantizas que no cambie la variable ClienteService una vez ejecutado el código
    private final AdministradorService administradorService;
    
    //devuelve la lista completa
    @GetMapping
    @Operation(summary = "Obtener todos los administradores", description = "Devuelve una lista con todos los administradores registrados")
    @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida correctamente")
    public ResponseEntity<List<AdministradorResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(administradorService.obtenerTodas());
    }

    //Busca por ID, si la encuentra la muestra, de lo contrario irá al GlobalException
    @GetMapping("/{rut}")
    @Operation(summary = "Obtener administrador por RUT", description = "Devuelve un administrador específico según su RUT")
    @ApiResponse(responseCode = "200", description = "Administrador obtenido correctamente")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    public ResponseEntity<AdministradorResponseDTO> obtenerPorId(@PathVariable ("rut") String rutAdministrador) {
        return administradorService.obtenerPorId(rutAdministrador)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Busca por estado
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener administradores por estado", description = "Devuelve una lista de administradores según su estado")
    @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida correctamente")
    @ApiResponse(responseCode = "204", description = "No hay administradores con ese estado")
    public ResponseEntity<List<AdministradorResponseDTO>> obtenerPorEstado(@PathVariable ("estado") String estado) {
        List<AdministradorResponseDTO> administradores = administradorService.obtenerPorEstado(estado);
        if (administradores.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay nadie con ese estado
        }
        return ResponseEntity.ok(administradores); // Devuelve 200 con la lista
    }

    //Recibe un JSON del cuerpo y valida que los datos estén bien (devuelve un 201) y crea un TipoServ
    @PostMapping
    @Operation(summary = "Crear un nuevo administrador", description = "Crea un nuevo administrador con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Administrador creado correctamente")
    public ResponseEntity<AdministradorResponseDTO> crear(@Valid @RequestBody AdministradorRequestDTO dto) {
        return ResponseEntity.status(201).body(administradorService.guardar(dto));
    }

    //busca el ID ingresado, si existe sobrescribe el dato anterior, de lo contrario devuelve un NotFound
    @PutMapping("/{rut}")
    @Operation(summary = "Actualizar administrador", description = "Actualiza un administrador existente según su RUT")
    @ApiResponse(responseCode = "200", description = "Administrador actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    public ResponseEntity<AdministradorResponseDTO> actualizar(
            @PathVariable ("rut") String rutAdministrador,
            @Valid @RequestBody AdministradorRequestDTO dto) {
        return administradorService.actualizar(rutAdministrador, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verifica  si el ID existe, si no existe responde un notFound, si existe lo elimina e indica un mensaje de elimiado
    @DeleteMapping("/{rut}")
    @Operation(summary = "Eliminar administrador", description = "Elimina un administrador existente según su RUT")
    @ApiResponse(responseCode = "200", description = "Administrador eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    public ResponseEntity<String> eliminar(@PathVariable ("rut") String rutAdministrador) {
        if (administradorService.obtenerPorId(rutAdministrador).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        administradorService.eliminar(rutAdministrador);
        return ResponseEntity.ok("El administrador con RUT " + rutAdministrador + " fue eliminado correctamente.");
    }
}
