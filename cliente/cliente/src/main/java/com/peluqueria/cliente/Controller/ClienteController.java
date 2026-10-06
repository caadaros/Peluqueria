package com.peluqueria.cliente.Controller;
//Controlador. Su trabajo es recibir las peticiones que llegan por internet (HTTP) y decidir qué hacer con ellas.

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import com.peluqueria.cliente.dto.ClienteRequestDTO;
import com.peluqueria.cliente.dto.ClienteResponseDTO;
import com.peluqueria.cliente.Service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente") //URL inicial
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operaciones de gestión de clientes")
public class ClienteController {
    //Al ser final, garantizas que no cambie la variable ClienteService una vez ejecutado el código
    private final ClienteService clienteService;
    
    //devuelve la lista completa
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista con todos los clientes registrados")
    @ApiResponse(responseCode = "200", description = "Clientes encontrados")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    //Busca por ID, si la encuentra la muestra, de lo contrario irá al GlobalException
    @GetMapping("/{rut}")
    @Operation(summary = "Obtener cliente por RUT", description = "Devuelve los datos del cliente correspondiente al RUT proporcionado")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable ("rut") String rutCliente) {
        return clienteService.obtenerPorId(rutCliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Recibe un JSON del cuerpo y valida que los datos estén bien (devuelve un 201) y crea un TipoServ
    @PostMapping
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Cliente creado")
    @ApiResponse(responseCode = "400", description = "Datos de cliente inválidos")
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(201).body(clienteService.guardar(dto));
    }

    //busca el ID ingresado, si existe sobrescribe el dato anterior, de lo contrario devuelve un NotFound
    @PutMapping("/{rut}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos del cliente correspondiente al RUT proporcionado")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable ("rut") String rutCliente,
            @Valid @RequestBody ClienteRequestDTO dto) {
        return clienteService.actualizar(rutCliente, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verifica  si el ID existe, si no existe responde un notFound, si existe lo elimina e indica un mensaje de elimiado
    @DeleteMapping("/{rut}")
    @Operation(summary = "Eliminar cliente", description = "Elimina el cliente correspondiente al RUT proporcionado")
    @ApiResponse(responseCode = "200", description = "Cliente eliminado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<String> eliminar(@PathVariable ("rut") String rutCliente) {
        if (clienteService.obtenerPorId(rutCliente).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.eliminar(rutCliente);
        return ResponseEntity.ok("El pcliente con RUT " + rutCliente + " fue eliminado correctamente.");

    }
}
