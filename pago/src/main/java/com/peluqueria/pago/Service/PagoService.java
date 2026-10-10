package com.peluqueria.pago.Service;

import com.peluqueria.pago.Model.Pago;
import com.peluqueria.pago.Repository.PagoRepository;
import com.peluqueria.pago.Security.TokenForwarder;
import com.peluqueria.pago.dto.BoletaRefDTO;
import com.peluqueria.pago.dto.PagoRequestDTO;
import com.peluqueria.pago.dto.PagoResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

// Requisito: registrar el pago de una boleta emitida por su monto exacto y marcarla como pagada.
@Slf4j
@Service
@RequiredArgsConstructor
public class PagoService {
    private final PagoRepository repository;
    private final WebClient webClientBoleta;
    private final WebClient webClientCliente;
    private final WebClient webClientNotificacion;

    private PagoResponseDTO mapToDTO(Pago p) {
        return new PagoResponseDTO(p.getIdPago(), p.getIdBoleta(), p.getRutCliente(), p.getMonto(),
                p.getMetodoPago(), p.getEstadoPago(), p.getFechaPago());
    }

    private BoletaRefDTO obtenerBoleta(Long idBoleta) {
        try {
            return webClientBoleta.get()
                    .uri("/api/boleta/{id}", idBoleta)
                    .headers(TokenForwarder.forward())
                    .retrieve()
                    .bodyToMono(BoletaRefDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("La boleta " + idBoleta + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar con Boleta: " + e.getMessage());
        }
    }

    private void validarCliente(String rutCliente) {
        try {
            webClientCliente.get()
                    .uri("/api/cliente/{rut}", rutCliente)
                    .headers(TokenForwarder.forward())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("El cliente con rut " + rutCliente + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar con Cliente: " + e.getMessage());
        }
    }

    private void marcarBoletaPagada(Long idBoleta) {
        try {
            webClientBoleta.patch()
                    .uri("/api/boleta/{id}/estado", idBoleta)
                    .headers(TokenForwarder.forward())
                    .bodyValue(Map.of("estadoBoleta", "Pagada"))
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo marcar la boleta como pagada: " + e.getMessage());
        }
    }

    private void notificar(Pago p) {
        try {
            webClientNotificacion.post()
                    .uri("/api/notificacion")
                    .headers(TokenForwarder.forward())
                    .bodyValue(Map.of(
                            "tipoNotificacion", "ESTADO_PAGO",
                            "rutCliente", p.getRutCliente(),
                            "detalle", "Pago aprobado de la boleta " + p.getIdBoleta() + "."))
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            log.warn(">>> No se pudo enviar la notificación de pago: {}", e.getMessage());
        }
    }

    public List<PagoResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<PagoResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public List<PagoResponseDTO> buscarPorBoleta(Long idBoleta) {
        return repository.findByIdBoleta(idBoleta).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<PagoResponseDTO> buscarPorCliente(String rutCliente) {
        return repository.findByRutCliente(rutCliente).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public PagoResponseDTO guardar(PagoRequestDTO dto) {
        BoletaRefDTO boleta = obtenerBoleta(dto.getIdBoleta());
        validarCliente(dto.getRutCliente());
        if (!dto.getRutCliente().equals(boleta.getRutCliente())) {
            throw new RuntimeException("La boleta pertenece a otro cliente.");
        }
        if (!"Emitida".equals(boleta.getEstadoBoleta())) {
            throw new RuntimeException("La boleta no está pendiente de pago.");
        }
        if (boleta.getTotal() != dto.getMonto()) {
            throw new RuntimeException("El monto debe ser igual al total de la boleta (" + boleta.getTotal() + ").");
        }
        Pago pago = new Pago(null, dto.getIdBoleta(), dto.getRutCliente(), dto.getMonto(), dto.getMetodoPago(),
                "APROBADO", LocalDateTime.now());
        Pago guardado = repository.save(pago);
        marcarBoletaPagada(dto.getIdBoleta());
        notificar(guardado);
        return mapToDTO(guardado);
    }
}
