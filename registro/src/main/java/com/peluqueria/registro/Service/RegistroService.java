package com.peluqueria.registro.Service;

import com.peluqueria.registro.Model.Registro;
import com.peluqueria.registro.Repository.RegistroRepository;
import com.peluqueria.registro.dto.RegistroRequestDTO;
import com.peluqueria.registro.dto.RegistroResponseDTO;
import com.peluqueria.registro.Security.TokenForwarder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroService {
    private final RegistroRepository repository;
    private final WebClient webClientCliente;
    private final WebClient webClientProfesional;
    private final WebClient webClientTipoServicio;
    private final WebClient webClientAgenda;
    private final WebClient webClientProducto;

    private RegistroResponseDTO mapToDTO(Registro e) {
        return new RegistroResponseDTO(
                e.getIdRegistro(),
                e.getIdAgenda(),
                e.getRutCliente(),
                e.getRutProfesional(),
                e.getIdTipoServicio(),
                e.getIdProducto(),
                e.getCantidadProducto(),
                e.getFecha(),
                e.getObservaciones());
    }

    private void validarExiste(WebClient cliente, String uri, String nombre, Object id) {
        try {
            cliente.get()
                    .uri(uri, id)
                    .headers(TokenForwarder.forward())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("El " + nombre + " " + id + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar con " + nombre + ": " + e.getMessage());
        }
    }

    private void validar(RegistroRequestDTO dto, Long idActual) {
        validarExiste(webClientCliente, "/api/cliente/{id}", "cliente", dto.getRutCliente());
        validarExiste(webClientProfesional, "/api/profesional/{id}", "profesional", dto.getRutProfesional());
        validarExiste(webClientTipoServicio, "/api/tiposervicio/{id}", "tipo de servicio", dto.getIdTipoServicio());
        if (dto.getIdAgenda() != null) {
            validarExiste(webClientAgenda, "/api/agenda/{id}", "cita", dto.getIdAgenda());
        }
        if (dto.getIdProducto() != null) {
            if (dto.getCantidadProducto() == null) {
                throw new RuntimeException("La cantidad es obligatoria cuando se informa un producto.");
            }
            validarExiste(webClientProducto, "/api/producto/{id}", "producto", dto.getIdProducto());
        }
    }

    public List<RegistroResponseDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<RegistroResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public List<RegistroResponseDTO> buscarPorCliente(String rutCliente) {
        return repository.findByRutCliente(rutCliente).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<RegistroResponseDTO> buscarPorProfesional(String rutProfesional) {
        return repository.findByRutProfesional(rutProfesional).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public RegistroResponseDTO guardar(RegistroRequestDTO dto) {
        validar(dto, null);
        Registro nuevo = new Registro(null, dto.getIdAgenda(), dto.getRutCliente(), dto.getRutProfesional(), dto.getIdTipoServicio(), dto.getIdProducto(), dto.getCantidadProducto(), dto.getFecha(), dto.getObservaciones());
        return mapToDTO(repository.save(nuevo));
    }

    public Optional<RegistroResponseDTO> actualizar(Long id, RegistroRequestDTO dto) {
        return repository.findById(id).map(existente -> {
            validar(dto, id);
            existente.setIdAgenda(dto.getIdAgenda());
            existente.setRutCliente(dto.getRutCliente());
            existente.setRutProfesional(dto.getRutProfesional());
            existente.setIdTipoServicio(dto.getIdTipoServicio());
            existente.setIdProducto(dto.getIdProducto());
            existente.setCantidadProducto(dto.getCantidadProducto());
            existente.setFecha(dto.getFecha());
            existente.setObservaciones(dto.getObservaciones());
            return mapToDTO(repository.save(existente));
        });
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
