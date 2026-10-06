package com.peluqueria.profesional.TestService;

import com.peluqueria.profesional.Model.Profesional;
import com.peluqueria.profesional.Repository.ProfesionalRepository;
import com.peluqueria.profesional.Service.ProfesionalService;
import com.peluqueria.profesional.dto.ProfesionalRequestDTO;
import com.peluqueria.profesional.dto.ProfesionalResponseDTO;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesionalServiceTest {

    @Mock ProfesionalRepository profesionalRepository;
    @InjectMocks ProfesionalService profesionalService;

    private final Faker faker = new Faker();
    private Profesional profesionalFake;
    private ProfesionalRequestDTO dtoFake;

    @BeforeEach
    void setUp() {
        String rut = faker.numerify("########-#");
        String[] especialidades = {"Colorista", "Peluqueria", "Mano"};
        profesionalFake = new Profesional(
            rut,
            faker.name().firstName(),
            faker.name().lastName(),
            faker.numerify("#########"),
            faker.internet().emailAddress(),
            faker.options().option(especialidades),
            "Activo"
        );
        dtoFake = new ProfesionalRequestDTO(
            profesionalFake.getRutProfesional(),
            profesionalFake.getNombreProfesional(),
            profesionalFake.getApellidoProfesional(),
            profesionalFake.getTelefonoProfesional(),
            profesionalFake.getCorreoProfesional(),
            profesionalFake.getEspecialidad(),
            profesionalFake.getEstado()
        );
    }

    @Test
    @DisplayName("obtenerTodas retorna lista")
    void obtenerTodas_retornaLista() {
        when(profesionalRepository.findAll()).thenReturn(List.of(profesionalFake));
        List<ProfesionalResponseDTO> resultado = profesionalService.obtenerTodas();
        assertThat(resultado).hasSize(1);
        verify(profesionalRepository).findAll();
    }

    @Test
    @DisplayName("guardar crea profesional correctamente")
    void guardar_creaProfesional() {
        when(profesionalRepository.save(any())).thenReturn(profesionalFake);
        ProfesionalResponseDTO resultado = profesionalService.guardar(dtoFake);
        assertThat(resultado.getRutProfesional()).isEqualTo(profesionalFake.getRutProfesional());
    }

    @Test
    @DisplayName("obtenerPorEspecialidad filtra correctamente")
    void obtenerPorEspecialidad_filtrado() {
        when(profesionalRepository.findByEspecialidad("Colorista"))
            .thenReturn(List.of(profesionalFake));
        List<ProfesionalResponseDTO> resultado = profesionalService.obtenerPorEspecialidad("Colorista");
        assertThat(resultado).isNotEmpty();
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_invocaDelete() {
        doNothing().when(profesionalRepository).deleteById(anyString());
        profesionalService.eliminar(profesionalFake.getRutProfesional());
        verify(profesionalRepository).deleteById(profesionalFake.getRutProfesional());
    }
}