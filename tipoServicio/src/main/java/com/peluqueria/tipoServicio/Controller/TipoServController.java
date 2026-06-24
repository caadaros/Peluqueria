package com.peluqueria.tipoServicio.Controller;
//Controlador. Su trabajo es recibir las peticiones que llegan por internet (HTTP) y decidir qué hacer con ellas.

import com.peluqueria.tipoServicio.dto.*;
import com.peluqueria.tipoServicio.Service.TipoServService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipoServicio") //URL inicial
@RequiredArgsConstructor
public class TipoServController {
    //Al ser final, garantizas que no cambie la variable TipoServService una vez ejecutado el código
    private final TipoServService tipoServ;
    
    //devuelve la lista completa
    @GetMapping
    public ResponseEntity<List<TipoServResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(tipoServ.obtenerTodas());
    }

    //Busca por ID, si la encuentra la muestra, de lo contrario irá al GlobalException
    @GetMapping("/{id}")
    public ResponseEntity<TipoServResponseDTO> obtenerPorId(@PathVariable Long idTipoServicio) {
        return tipoServ.obtenerPorId(idTipoServicio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Recibe un JSON del cuerpo y valida que los datos estén bien (devuelve un 201) y crea un TipoServ
    @PostMapping
    public ResponseEntity<TipoServResponseDTO> crear(@Valid @RequestBody TipoServRequestDTO TipoServ) {
        return ResponseEntity.status(201).body(tipoServ.guardar(TipoServ));
    }

    //busca el ID ingresado, si existe sobrescribe el dato anterior, de lo contrario devuelve un NotFound
    @PutMapping("/{id}")
    public ResponseEntity<TipoServResponseDTO> actualizar(
            @PathVariable Long idTipoServicio,
            @Valid @RequestBody TipoServRequestDTO dto) {
        return tipoServ.actualizar(idTipoServicio, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verifica  si el ID existe, si no existe responde un notFound, si existe lo elimina e indica un mensaje de elimiado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idTipoServicio) {
        if (tipoServ.obtenerPorId(idTipoServicio).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tipoServ.eliminar(idTipoServicio);
        return ResponseEntity.noContent().build();
    }
}
