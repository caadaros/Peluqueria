package com.peluqueria.boleta.Service;

import com.peluqueria.boleta.Model.Boleta;
import com.peluqueria.boleta.Model.DetalleBoleta;
import com.peluqueria.boleta.Repository.BoletaRepository;
import com.peluqueria.boleta.Security.TokenForwarder;
import com.peluqueria.boleta.dto.BoletaRequestDTO;
import com.peluqueria.boleta.dto.BoletaResponseDTO;
import com.peluqueria.boleta.dto.DetalleRequestDTO;
import com.peluqueria.boleta.dto.DetalleResponseDTO;
import com.peluqueria.boleta.dto.ProductoRefDTO;
import com.peluqueria.boleta.dto.TipoServRefDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

// Requisito: emitir boletas con precios tomados de servicios y productos; las boletas no se editan ni se eliminan.
@Slf4j
@Service
@RequiredArgsConstructor
public class BoletaService {
    private final BoletaRepository repository;
    private final WebClient webClientCliente;
    private final WebClient webClientProfesional;
    private final WebClient webClientTipoServicio;
    private final WebClient webClientProducto;
    private final WebClient webClientAgenda;

    private BoletaResponseDTO mapToDTO(Boleta b) {
        List<DetalleResponseDTO> lineas = b.getDetalles().stream()
                .map(d -> new DetalleResponseDTO(d.getTipoItem(), d.getIdItem(), d.getDescripcion(),
                        d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal()))
                .collect(Collectors.toList());
        return new BoletaResponseDTO(b.getIdBoleta(), b.getFechaEmision(), b.getRutCliente(), b.getRutProfesional(),
                b.getIdAgenda(), b.getTotal(), b.getEstadoBoleta(), lineas);
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

    private <T> T obtenerRef(WebClient cliente, String uri, String nombre, Long id, Class<T> tipo) {
        try {
            return cliente.get()
                    .uri(uri, id)
                    .headers(TokenForwarder.forward())
                    .retrieve()
                    .bodyToMono(tipo)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("El " + nombre + " " + id + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar con " + nombre + ": " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<BoletaResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BoletaResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<BoletaResponseDTO> buscarPorCliente(String rutCliente) {
        return repository.findByRutCliente(rutCliente).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public BoletaResponseDTO guardar(BoletaRequestDTO dto) {
        validarExiste(webClientCliente, "/api/cliente/{id}", "cliente", dto.getRutCliente());
        validarExiste(webClientProfesional, "/api/profesional/{id}", "profesional", dto.getRutProfesional());
        if (dto.getIdAgenda() != null) {
            validarExiste(webClientAgenda, "/api/agenda/{id}", "cita", dto.getIdAgenda());
        }
        Boleta boleta = new Boleta();
        boleta.setFechaEmision(LocalDateTime.now());
        boleta.setRutCliente(dto.getRutCliente());
        boleta.setRutProfesional(dto.getRutProfesional());
        boleta.setIdAgenda(dto.getIdAgenda());
        boleta.setEstadoBoleta("Emitida");
        int total = 0;
        for (DetalleRequestDTO linea : dto.getDetalles()) {
            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setTipoItem(linea.getTipoItem());
            detalle.setIdItem(linea.getIdItem());
            detalle.setCantidad(linea.getCantidad());
            if ("SERVICIO".equals(linea.getTipoItem())) {
                TipoServRefDTO servicio = obtenerRef(webClientTipoServicio, "/api/tiposervicio/{id}",
                        "tipo de servicio", linea.getIdItem(), TipoServRefDTO.class);
                detalle.setDescripcion(servicio.getDescripcionServicio());
                detalle.setPrecioUnitario(servicio.getPrecioServicio());
            } else {
                ProductoRefDTO producto = obtenerRef(webClientProducto, "/api/producto/{id}",
                        "producto", linea.getIdItem(), ProductoRefDTO.class);
                detalle.setDescripcion(producto.getNombreProducto());
                detalle.setPrecioUnitario(producto.getPrecioProducto());
            }
            detalle.setSubtotal(detalle.getPrecioUnitario() * detalle.getCantidad());
            total += detalle.getSubtotal();
            boleta.agregarDetalle(detalle);
        }
        boleta.setTotal(total);
        return mapToDTO(repository.save(boleta));
    }

    @Transactional
    public Optional<BoletaResponseDTO> cambiarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(boleta -> {
            if (!"Emitida".equals(boleta.getEstadoBoleta())) {
                throw new RuntimeException("Solo una boleta emitida puede cambiar de estado.");
            }
            boleta.setEstadoBoleta(nuevoEstado);
            return mapToDTO(repository.save(boleta));
        });
    }
}
