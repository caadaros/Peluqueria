package com.peluqueria.auth.Service;

import com.peluqueria.auth.Model.Usuario;
import com.peluqueria.auth.Repository.UsuarioRepository;
import com.peluqueria.auth.Security.JwtService;
import com.peluqueria.auth.dto.LoginRequestDTO;
import com.peluqueria.auth.dto.RegistroRequestDTO;
import com.peluqueria.auth.dto.TokenResponseDTO;
import com.peluqueria.auth.dto.UsuarioResponseDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Requisito: validar credenciales con BCrypt y emitir JWT con el rol; registrar usuarios nuevos.
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public Optional<TokenResponseDTO> login(LoginRequestDTO dto) {
        return repository.findByUsername(dto.getUsername())
                .filter(Usuario::isActivo)
                .filter(u -> encoder.matches(dto.getPassword(), u.getPasswordHash()))
                .map(u -> new TokenResponseDTO(jwtService.generarToken(u.getUsername(), u.getRol()),
                        "Bearer", jwtService.getExpirationMs(), u.getRol()));
    }

    public UsuarioResponseDTO registrar(RegistroRequestDTO dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El usuario " + dto.getUsername() + " ya existe.");
        }
        Usuario usuario = new Usuario(null, dto.getUsername(), encoder.encode(dto.getPassword()), dto.getRol(), true);
        Usuario guardado = repository.save(usuario);
        return new UsuarioResponseDTO(guardado.getIdUsuario(), guardado.getUsername(), guardado.getRol(), guardado.isActivo());
    }
}
