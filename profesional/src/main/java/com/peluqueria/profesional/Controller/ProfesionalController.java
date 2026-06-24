package com.peluqueria.profesional.Controller;
//Controlador. Su trabajo es recibir las peticiones que llegan por internet (HTTP) y decidir qué hacer con ellas.

import com.peluqueria.profesional.dto.*;
import com.peluqueria.profesional.Service.ProfesionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profesional") //URL inicial
@RequiredArgsConstructor
public class ProfesionalController {
    //Al ser final, garantizas que no cambie la variable ClienteService una vez ejecutado el código
    private final ProfesionalService profesionalService;
    
    //devuelve la lista completa
    @GetMapping
    public ResponseEntity<List<ProfesionalResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(profesionalService.obtenerTodas());
    }

    //Busca por ID, si la encuentra la muestra, de lo contrario irá al GlobalException
    @GetMapping("/{rut}")
    public ResponseEntity<ProfesionalResponseDTO> obtenerPorId(@PathVariable ("rut") String rutProfesional) {
        return profesionalService.obtenerPorId(rutProfesional)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Busca por especialidad
    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<ProfesionalResponseDTO>> obtenerPorEspecialidad(@PathVariable ("especialidad") String especialidad) {
        List<ProfesionalResponseDTO> profesionales = profesionalService.obtenerPorEspecialidad(especialidad);
        if (profesionales.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay nadie con esa especialidad
        }
        return ResponseEntity.ok(profesionales); // Devuelve 200 con la lista
    }

    //Busca por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProfesionalResponseDTO>> obtenerPorEstado(@PathVariable ("estado") String estado) {
        List<ProfesionalResponseDTO> profesionales = profesionalService.obtenerPorEstado(estado);
        if (profesionales.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay nadie con esa especialidad
        }
        return ResponseEntity.ok(profesionales); // Devuelve 200 con la lista
    }

    //Recibe un JSON del cuerpo y valida que los datos estén bien (devuelve un 201) y crea un TipoServ
    @PostMapping
    public ResponseEntity<ProfesionalResponseDTO> crear(@Valid @RequestBody ProfesionalRequestDTO dto) {
        return ResponseEntity.status(201).body(profesionalService.guardar(dto));
    }

    //busca el ID ingresado, si existe sobrescribe el dato anterior, de lo contrario devuelve un NotFound
    @PutMapping("/{rut}")
    public ResponseEntity<ProfesionalResponseDTO> actualizar(
            @PathVariable ("rut") String rutProfesional,
            @Valid @RequestBody ProfesionalRequestDTO dto) {
        return profesionalService.actualizar(rutProfesional, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verifica  si el ID existe, si no existe responde un notFound, si existe lo elimina e indica un mensaje de elimiado
    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminar(@PathVariable ("rut") String rutProfesional) {
        if (profesionalService.obtenerPorId(rutProfesional).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        profesionalService.eliminar(rutProfesional);
        return ResponseEntity.ok("El profesional con RUT " + rutProfesional + " fue eliminado correctamente.");
    }
}
