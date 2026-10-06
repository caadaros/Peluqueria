package com.peluqueria.producto.TestService;

import com.peluqueria.producto.Model.Producto;
import com.peluqueria.producto.Repository.ProductoRepository;
import com.peluqueria.producto.Service.ProductoService;
import com.peluqueria.producto.dto.ProductoRequestDTO;
import com.peluqueria.producto.dto.ProductoResponseDTO;
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
class ProductoServiceTest {

    @Mock ProductoRepository productoRepository;
    @InjectMocks ProductoService productoService;

    private final Faker faker = new Faker();
    private Producto productoFake;
    private ProductoRequestDTO dtoFake;

    @BeforeEach
    void setUp() {
        productoFake = new Producto(
            faker.random().nextLong(1, 100),
            faker.commerce().productName(),
            faker.number().numberBetween(5000, 100000),
            faker.options().option("Unidad", "ML", "bolsa", "caja")
        );
        dtoFake = new ProductoRequestDTO(
            productoFake.getDescripcionProducto(),
            productoFake.getPrecioProducto(),
            productoFake.getUnidadMedida()
        );
    }

    @Test
    @DisplayName("obtenerTodas devuelve todos los productos")
    void obtenerTodas_devuelveLista() {
        when(productoRepository.findAll()).thenReturn(List.of(productoFake));
        List<ProductoResponseDTO> resultado = productoService.obtenerTodas();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDescripcionProducto())
            .isEqualTo(productoFake.getDescripcionProducto());
    }

    @Test
    @DisplayName("obtenerPorId retorna el producto correcto")
    void obtenerPorId_retornaProducto() {
        when(productoRepository.findById(productoFake.getIdProducto()))
            .thenReturn(Optional.of(productoFake));
        Optional<ProductoResponseDTO> resultado =
            productoService.obtenerPorId(productoFake.getIdProducto());
        assertThat(resultado).isPresent();
    }

    @Test
    @DisplayName("guardar persiste producto y retorna DTO")
    void guardar_persisteProducto() {
        when(productoRepository.save(any())).thenReturn(productoFake);
        ProductoResponseDTO resultado = productoService.guardar(dtoFake);
        assertThat(resultado.getDescripcionProducto()).isEqualTo(productoFake.getDescripcionProducto());
    }

    @Test
    @DisplayName("eliminar llama deleteById")
    void eliminar_llamaDelete() {
        doNothing().when(productoRepository).deleteById(anyLong());
        productoService.eliminar(productoFake.getIdProducto());
        verify(productoRepository).deleteById(productoFake.getIdProducto());
    }
}
