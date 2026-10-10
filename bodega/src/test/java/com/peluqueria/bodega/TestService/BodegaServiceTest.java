package com.peluqueria.bodega.TestService;

import com.peluqueria.bodega.Model.Bodega;
import com.peluqueria.bodega.Repository.BodegaRepository;
import com.peluqueria.bodega.Service.BodegaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BodegaServiceTest {

    @Mock BodegaRepository repository;
    @InjectMocks BodegaService service;

    private Bodega fake;

    @BeforeEach
    void setUp() {
        fake = new Bodega(null, "Central", "Dirección", "Activa");
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
