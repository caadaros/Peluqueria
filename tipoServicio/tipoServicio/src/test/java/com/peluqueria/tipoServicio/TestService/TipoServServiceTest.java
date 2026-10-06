package com.peluqueria.tipoServicio.TestService;

import com.peluqueria.tipoServicio.Model.TipoServicio;
import com.peluqueria.tipoServicio.Repository.TipoServRepository;
import com.peluqueria.tipoServicio.Service.TipoServService;
import com.peluqueria.tipoServicio.dto.TipoServRequestDTO;
import com.peluqueria.tipoServicio.dto.TipoServResponseDTO;
import net.datafaker.Faker;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoServServiceTest {

    @Mock TipoServRepository tipoServRepository;
    @InjectMocks TipoServService tipoServService;

    private final Faker faker = new Faker();
    private TipoServicio servicioFake;
    private TipoServRequestDTO dtoFake;

    @BeforeEach
    void setUp() {
        servicioFake = new TipoServicio(
            faker.random().nextLong(1, 100),
            faker.commerce().productName(),
            faker.number().numberBetween(5000, 100000),
            faker.random().nextFloat() + 1,
            faker.options().option("Colorista", "Peluqueria", "Mano")
        );
        dtoFake = new TipoServRequestDTO(
            servicioFake.getDescripcionServicio(),
            servicioFake.getPrecioServicio(),
            servicioFake.getDuracionHoras(),
            servicioFake.getEspecialidadAsociada()
        );
    }

    @Test
    @DisplayName("obtenerTodas devuelve todos los servicios")
    void obtenerTodas_devuelveLista() {
        when(tipoServRepository.findAll()).thenReturn(List.of(servicioFake));
        List<TipoServResponseDTO> resultado = tipoServService.obtenerTodas();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDescripcionServicio())
            .isEqualTo(servicioFake.getDescripcionServicio());
    }

    @Test
    @DisplayName("obtenerPorId retorna el servicio correcto")
    void obtenerPorId_retornaServicio() {
        when(tipoServRepository.findById(servicioFake.getIdTipoServicio()))
            .thenReturn(Optional.of(servicioFake));
        Optional<TipoServResponseDTO> resultado =
            tipoServService.obtenerPorId(servicioFake.getIdTipoServicio());
        assertThat(resultado).isPresent();
    }

    @Test
    @DisplayName("guardar persiste tipo de servicio")
    void guardar_persisteServicio() {
        when(tipoServRepository.save(any())).thenReturn(servicioFake);
        TipoServResponseDTO resultado = tipoServService.guardar(dtoFake);
        assertThat(resultado.getPrecioServicio()).isPositive();
    }

    @Test
    @DisplayName("eliminar llama deleteById")
    void eliminar_llamaDelete() {
        doNothing().when(tipoServRepository).deleteById(anyLong());
        tipoServService.eliminar(servicioFake.getIdTipoServicio());
        verify(tipoServRepository).deleteById(servicioFake.getIdTipoServicio());
    }
}
