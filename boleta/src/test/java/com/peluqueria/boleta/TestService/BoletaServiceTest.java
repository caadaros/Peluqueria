package com.peluqueria.boleta.TestService;

import com.peluqueria.boleta.Model.Boleta;
import com.peluqueria.boleta.Repository.BoletaRepository;
import com.peluqueria.boleta.Service.BoletaService;
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
class BoletaServiceTest {

    @Mock BoletaRepository repository;
    @Mock WebClient webClientCliente;
    @Mock WebClient webClientProfesional;
    @Mock WebClient webClientTipoServicio;
    @Mock WebClient webClientProducto;
    @Mock WebClient webClientAgenda;
    @InjectMocks BoletaService service;

    @Test
    @DisplayName("obtenerTodas retorna la lista del repositorio")
    void obtenerTodas_retornaLista() {
        when(repository.findAll()).thenReturn(List.of());
        assertThat(service.obtenerTodas()).hasSize(0);
    }

    @Test
    @DisplayName("obtenerPorId retorna vacío si no existe")
    void obtenerPorId_noExiste_retornaVacio() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThat(service.obtenerPorId(1L)).isEmpty();
    }
}
