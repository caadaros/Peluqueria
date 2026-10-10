package com.peluqueria.notificacion.Service;

import com.peluqueria.notificacion.Model.Notificacion;
import com.peluqueria.notificacion.Repository.NotificacionRepository;
import com.peluqueria.notificacion.Security.TokenForwarder;
import com.peluqueria.notificacion.dto.ClienteRefDTO;
import com.peluqueria.notificacion.dto.NotificacionRequestDTO;
import com.peluqueria.notificacion.dto.NotificacionResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

// Requisito: componer y registrar notificaciones por tipo; el envío real al canal es simulado (log).
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {
    private final NotificacionRepository repository;
    private final WebClient webClientCliente;

    private NotificacionResponseDTO mapToDTO(Notificacion n) {
        return new NotificacionResponseDTO(
                n.getIdNotificacion(), n.getTipoNotificacion(), n.getRutCliente(), n.getDetalle(),
                n.getCorreoDestino(), n.getMensaje(), n.getEstadoEnvio(), n.getFechaEnvio());
    }

    private ClienteRefDTO obtenerCliente(String rutCliente) {
        try {
            return webClientCliente.get()
                    .uri("/api/cliente/{rut}", rutCliente)
                    .headers(TokenForwarder.forward())
                    .retrieve()
                    .bodyToMono(ClienteRefDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("El cliente con rut " + rutCliente + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException("No se puede conectar con Cliente: " + e.getMessage());
        }
    }

    private String componer(String tipo, String nombre, String detalle) {
        String base = switch (tipo) {
            case "CONFIRMACION_RESERVA" -> "Hola " + nombre + ", tu reserva fue confirmada.";
            case "RECORDATORIO_CITA" -> "Hola " + nombre + ", te recordamos tu próxima cita.";
            case "CANCELACION" -> "Hola " + nombre + ", tu reserva fue cancelada.";
            default -> "Hola " + nombre + ", actualizamos el estado de tu pago.";
        };
        return detalle == null || detalle.isBlank() ? base : base + " " + detalle;
    }

    public List<NotificacionResponseDTO> obtenerTodas() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<NotificacionResponseDTO> obtenerPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO);
    }

    public List<NotificacionResponseDTO> buscarPorCliente(String rutCliente) {
        return repository.findByRutCliente(rutCliente).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public NotificacionResponseDTO guardar(NotificacionRequestDTO dto) {
        ClienteRefDTO cliente = obtenerCliente(dto.getRutCliente());
        Notificacion notificacion = new Notificacion(null, dto.getTipoNotificacion(), dto.getRutCliente(), dto.getDetalle(),
                cliente.getCorreoCliente(), componer(dto.getTipoNotificacion(), cliente.getNombreCliente(), dto.getDetalle()),
                "ENVIADA", LocalDateTime.now());
        log.info(">>> Notificación {} registrada para el cliente {}", dto.getTipoNotificacion(), dto.getRutCliente());
        return mapToDTO(repository.save(notificacion));
    }
}
