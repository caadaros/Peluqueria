package com.peluqueria.auth.Controller;

import com.peluqueria.auth.Service.AuthService;
import com.peluqueria.auth.dto.LoginRequestDTO;
import com.peluqueria.auth.dto.RegistroRequestDTO;
import com.peluqueria.auth.dto.TokenResponseDTO;
import com.peluqueria.auth.dto.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Autenticación y registro de usuarios")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Devuelve un JWT si las credenciales son correctas")
    @ApiResponse(responseCode = "200", description = "Token emitido")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        return service.login(dto)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas")));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Solo disponible para el rol ADMIN")
    @ApiResponse(responseCode = "201", description = "Usuario creado")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.status(201).body(service.registrar(dto));
    }

    @GetMapping("/me")
    @Operation(summary = "Usuario y rol del token actual")
    public ResponseEntity<Map<String, String>> me(Authentication autenticacion) {
        String rol = autenticacion.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("");
        return ResponseEntity.ok(Map.of("username", autenticacion.getName(), "rol", rol));
    }
}
