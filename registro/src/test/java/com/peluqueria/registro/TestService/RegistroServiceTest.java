package com.peluqueria.registro.TestService;

import com.peluqueria.registro.Model.Registro;
import com.peluqueria.registro.Repository.RegistroRepository;
import com.peluqueria.registro.Service.RegistroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroServiceTest {

    @Mock RegistroRepository repository;
    @Mock WebClient webClientCliente;
    @Mock WebClient webClientProfesional;
    @Mock WebClient webClientTipoServicio;
    @Mock WebClient webClientAgenda;
    @Mock WebClient webClientProducto;
    @InjectMocks RegistroService service;

    private Registro fake;

    @BeforeEach
    void setUp() {
        fake = new Registro(null, 1, "111111111", "222222221", 1L, null, null, "2026-05-25", "obs");
    }

    @Test
    @DisplayName("obtenerTodas retorna la lista del repositorio")
    void obtenerTodas_retornaLista() {
        when(repository.findAll()).thenReturn(List.of(fake));
        assertThat(service.obtenerTodas()).hasSize(1);
    }

    @Test
    @DisplayName("obtenerPorId retorna vacío si no existe")
    void obtenerPorId_noExiste_retornaVacio() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThat(service.obtenerPorId(1L)).isEmpty();
    }
}
