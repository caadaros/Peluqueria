package com.peluqueria.administrador.TestService;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.peluqueria.administrador.Model.Administrador;
import com.peluqueria.administrador.Repository.AdministradorRepository;
import com.peluqueria.administrador.Service.AdministradorService;
import com.peluqueria.administrador.dto.AdministradorRequestDTO;
import com.peluqueria.administrador.dto.AdministradorResponseDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministradorServiceTest {

    @Mock AdministradorRepository administradorRepository;
    @InjectMocks AdministradorService administradorService;

    private final Faker faker = new Faker();
    private Administrador administradorFake;
    private AdministradorRequestDTO dtoFake;

    @BeforeEach
    void setUp() {
        String rut = faker.numerify("########-#");
        administradorFake = new Administrador(
            rut,
            faker.name().firstName(),
            faker.name().lastName(),
            faker.numerify("#########"),
            faker.internet().emailAddress(),
            faker.internet().password(8, 20),
            "Activo"
        );
        dtoFake = new AdministradorRequestDTO(
            administradorFake.getRutAdministrador(),
            administradorFake.getNombreAdministrador(),
            administradorFake.getApellidoAdministrador(),
            administradorFake.getTelefonoAdministrador(),
            administradorFake.getCorreoAdministrador(),
            administradorFake.getPasswordAdministrador(),
            administradorFake.getEstado()
        );
    }

    @Test
    @DisplayName("obtenerTodas retorna lista")
    void obtenerTodas_retornaLista() {
        when(administradorRepository.findAll()).thenReturn(List.of(administradorFake));
        List<AdministradorResponseDTO> resultado = administradorService.obtenerTodas();
        assertThat(resultado).hasSize(1);
        verify(administradorRepository).findAll();
    }

    @Test
    @DisplayName("guardar crea administrador correctamente")
    void guardar_creaAdministrador() {
        when(administradorRepository.save(any())).thenReturn(administradorFake);
        AdministradorResponseDTO resultado = administradorService.guardar(dtoFake);
        assertThat(resultado.getRutAdministrador()).isEqualTo(administradorFake.getRutAdministrador());
    }

    @Test
    @DisplayName("obtenerPorEstado filtra correctamente")
    void obtenerPorEstado_filtrado() {
        when(administradorRepository.findByEstado("Activo"))
            .thenReturn(List.of(administradorFake));
        List<AdministradorResponseDTO> resultado = administradorService.obtenerPorEstado("Activo");
        assertThat(resultado).isNotEmpty();
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_invocaDelete() {
        doNothing().when(administradorRepository).deleteById(anyString());
        administradorService.eliminar(administradorFake.getRutAdministrador());
        verify(administradorRepository).deleteById(administradorFake.getRutAdministrador());
    }
}