package com.peluqueria.auth.TestService;

import com.peluqueria.auth.Model.Usuario;
import com.peluqueria.auth.Repository.UsuarioRepository;
import com.peluqueria.auth.Security.JwtService;
import com.peluqueria.auth.Service.AuthService;
import com.peluqueria.auth.dto.LoginRequestDTO;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UsuarioRepository repository;
    @Mock PasswordEncoder encoder;
    @Mock JwtService jwtService;
    @InjectMocks AuthService service;

    @Test
    @DisplayName("login con credenciales correctas devuelve token")
    void login_correcto_devuelveToken() {
        String clave = UUID.randomUUID().toString();
        Usuario usuario = new Usuario(1L, "usuario", "hash", "ADMIN", true);
        when(repository.findByUsername("usuario")).thenReturn(Optional.of(usuario));
        when(encoder.matches(clave, "hash")).thenReturn(true);
        when(jwtService.generarToken("usuario", "ADMIN")).thenReturn("token");
        when(jwtService.getExpirationMs()).thenReturn(900000L);
        assertThat(service.login(new LoginRequestDTO("usuario", clave))).isPresent();
    }

    @Test
    @DisplayName("login con clave incorrecta devuelve vacío")
    void login_claveIncorrecta_devuelveVacio() {
        Usuario usuario = new Usuario(1L, "usuario", "hash", "ADMIN", true);
        when(repository.findByUsername("usuario")).thenReturn(Optional.of(usuario));
        when(encoder.matches(anyString(), anyString())).thenReturn(false);
        assertThat(service.login(new LoginRequestDTO("usuario", UUID.randomUUID().toString()))).isEmpty();
    }
}
