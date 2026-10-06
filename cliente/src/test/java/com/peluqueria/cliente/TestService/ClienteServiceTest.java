package com.peluqueria.cliente.TestService;

import com.peluqueria.cliente.Model.Cliente;
import com.peluqueria.cliente.Repository.ClienteRepository;
import com.peluqueria.cliente.Service.ClienteService;
import com.peluqueria.cliente.dto.ClienteRequestDTO;
import com.peluqueria.cliente.dto.ClienteResponseDTO;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private final Faker faker = new Faker();
    private Cliente clienteFake;
    private ClienteRequestDTO dtoFake;

    @BeforeEach
    void setUp() {
        String rut = faker.numerify("########-#");
        clienteFake = new Cliente(
            rut,
            faker.name().firstName(),
            faker.name().lastName(),
            faker.numerify("#########"),
            faker.internet().emailAddress()
        );
        dtoFake = new ClienteRequestDTO(
            clienteFake.getRutCliente(),
            clienteFake.getNombreCliente(),
            clienteFake.getApellidoCliente(),
            clienteFake.getTelefonoCliente(),
            clienteFake.getCorreoCliente()
        );
    }

    @Test
    @DisplayName("obtenerTodos retorna lista con clientes")
    void obtenerTodos_retornaLista() {
        when(clienteRepository.findAll()).thenReturn(List.of(clienteFake));
        List<ClienteResponseDTO> resultado = clienteService.obtenerTodos();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getRutCliente()).isEqualTo(clienteFake.getRutCliente());
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("guardar persiste y retorna el cliente")
    void guardar_persisteCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteFake);
        ClienteResponseDTO resultado = clienteService.guardar(dtoFake);
        assertThat(resultado.getNombreCliente()).isEqualTo(clienteFake.getNombreCliente());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("obtenerPorId retorna vacío si no existe")
    void obtenerPorId_noExiste_retornaVacio() {
        when(clienteRepository.findById(anyString())).thenReturn(Optional.empty());
        Optional<ClienteResponseDTO> resultado = clienteService.obtenerPorId("999");
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("eliminar llama a deleteById")
    void eliminar_llamaDeleteById() {
        doNothing().when(clienteRepository).deleteById(anyString());
        clienteService.eliminar(clienteFake.getRutCliente());
        verify(clienteRepository).deleteById(clienteFake.getRutCliente());
    }
}
