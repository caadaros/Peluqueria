package com.peluqueria.kardex.Service;

import com.peluqueria.kardex.Model.Movimiento;
import com.peluqueria.kardex.Repository.MovimientoRepository;
import com.peluqueria.kardex.Security.TokenForwarder;
import com.peluqueria.kardex.dto.MovimientoRequestDTO;
import com.peluqueria.kardex.dto.MovimientoResponseDTO;
import com.peluqueria.kardex.dto.StockResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

// Requisito: registrar entradas y salidas de inventario sin permitir stock negativo; el kardex es inmutable.
@Slf4j
@Service
@RequiredArgsConstructor
public class MovimientoService {
    private final MovimientoRepository repository;
    private final WebClient webClientProducto;
    private final WebClient webClientBodega;

    private MovimientoResponseDTO mapToDTO(Movimiento m) {
        return new MovimientoResponseDTO(
                m.getIdMovimiento(), m.getIdProducto(), m.getIdBodega(), m.getTipoMovimiento(),
                m.getCantidad(), m.getMotivo(), m.getFechaMovimiento(), m.getRegistradoPor());
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

    private long calcularStock(Long idProducto, Long idBodega) {
        return repository.findByIdProductoAndIdBodega(idProducto, idBodega).stream()
                .mapToLong(m -> "ENTRADA".equals(m.getTipoMovimiento()) ? m.getCantidad() : -m.getCantidad())
                .sum();
    }

    public List<MovimientoResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<MovimientoResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public List<MovimientoResponseDTO> buscarPorProducto(Long idProducto) {
        return repository.findByIdProducto(idProducto).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MovimientoResponseDTO> buscarPorBodega(Long idBodega) {
        return repository.findByIdBodega(idBodega).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public StockResponseDTO obtenerStock(Long idProducto, Long idBodega) {
        return new StockResponseDTO(idProducto, idBodega, calcularStock(idProducto, idBodega));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public MovimientoResponseDTO guardar(MovimientoRequestDTO dto) {
        validarExiste(webClientProducto, "/api/producto/{id}", "producto", dto.getIdProducto());
        validarExiste(webClientBodega, "/api/bodega/{id}", "bodega", dto.getIdBodega());
        if ("SALIDA".equals(dto.getTipoMovimiento())
                && calcularStock(dto.getIdProducto(), dto.getIdBodega()) < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para registrar la salida.");
        }
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
        Movimiento movimiento = new Movimiento(null, dto.getIdProducto(), dto.getIdBodega(), dto.getTipoMovimiento(),
                dto.getCantidad(), dto.getMotivo(), LocalDateTime.now(),
                autenticacion == null ? null : autenticacion.getName());
        return mapToDTO(repository.save(movimiento));
    }
}
