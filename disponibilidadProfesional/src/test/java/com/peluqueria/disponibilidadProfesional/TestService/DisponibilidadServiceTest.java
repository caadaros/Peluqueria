package com.peluqueria.disponibilidadProfesional.TestService;

import com.peluqueria.disponibilidadProfesional.Model.DisponibilidadProfesional;
import com.peluqueria.disponibilidadProfesional.Repository.DisponibilidadRepository;
import com.peluqueria.disponibilidadProfesional.Service.DisponibilidadService;
import com.peluqueria.disponibilidadProfesional.dto.DisponibilidadResponseDTO;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisponibilidadServiceTest {

    @Mock DisponibilidadRepository repository;
    // WebClient se mockea manualmente porque usa encadenamiento de builders
    @Mock WebClient webClient;
    @InjectMocks DisponibilidadService service;

    private final Faker faker = new Faker();
    private DisponibilidadProfesional dispFake;

    @BeforeEach
    void setUp() {
        dispFake = new DisponibilidadProfesional(
            faker.random().nextLong(1, 100),
            faker.numerify("########-#"),
            "2026-08-" + faker.number().numberBetween(1, 28),
            "09:00",
            "18:00"
        );
    }

    @Test
    @DisplayName("obtenerTodas retorna disponibilidades")
    void obtenerTodas_retornaLista() {
        when(repository.findAll()).thenReturn(List.of(dispFake));
        List<DisponibilidadResponseDTO> resultado = service.obtenerTodas();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getRutProfesional()).isEqualTo(dispFake.getRutProfesional());
    }

    @Test
    @DisplayName("estaDisponible retorna true si hora cae en rango")
    void estaDisponible_horaEnRango_retornaTrue() {
        when(repository.findByRutProfesionalAndFecha(any(), any()))
            .thenReturn(List.of(dispFake));
        boolean disponible = service.estaDisponible(
            dispFake.getRutProfesional(), dispFake.getFecha(), "10:00");
        assertThat(disponible).isTrue();
    }

    @Test
    @DisplayName("estaDisponible retorna false si hora fuera de rango")
    void estaDisponible_horaFueraRango_retornaFalse() {
        when(repository.findByRutProfesionalAndFecha(any(), any()))
            .thenReturn(List.of(dispFake));
        boolean disponible = service.estaDisponible(
            dispFake.getRutProfesional(), dispFake.getFecha(), "20:00");
        assertThat(disponible).isFalse();
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_invocaDelete() {
        doNothing().when(repository).deleteById(anyLong());
        service.eliminar(dispFake.getIdDisponibilidad());
        verify(repository).deleteById(dispFake.getIdDisponibilidad());
    }
}