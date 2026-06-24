package com.peluqueria.disponibilidadProfesional.Service;

import com.peluqueria.disponibilidadProfesional.Repository.DisponibilidadRepository;
import com.peluqueria.disponibilidadProfesional.Model.DisponibilidadProfesional;
import com.peluqueria.disponibilidadProfesional.dto.DisponibilidadRequestDTO;
import com.peluqueria.disponibilidadProfesional.dto.DisponibilidadResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisponibilidadService {
    private final DisponibilidadRepository repository;
    private final WebClient webClient;

    // ═══════════════════════════════════════════════════
    // COMUNICACIÓN CON profesional VÍA WEBCLIENT
    private void validarProfesional(String rutProfesional) {
        try {
            webClient.get()
                    .uri("/api/profesional/{id}", rutProfesional)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info(">>> Profesional {} validado correctamente (WebClient)", rutProfesional);

        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException(
                    "El profesional, rut " + rutProfesional + " no existe.");
        } catch (Exception e) {
            throw new RuntimeException(
                    "No se puede conectar: " + e.getMessage());
        }
    }

    // ── Mapeo Entidad → ResponseDTO ──────────────────
    private DisponibilidadResponseDTO mapToDTO(DisponibilidadProfesional disp) {
        return new DisponibilidadResponseDTO(
                disp.getIdDisponibilidad(),
                disp.getRutProfesional(),
                disp.getFecha(),
                disp.getHoraInicio(), 
                disp.getHoraFin());
    }

    public boolean estaDisponible(String rutProfesional, String fecha, String hora) {
        List<DisponibilidadProfesional> horarios = 
            repository.findByRutProfesionalAndFecha(rutProfesional, fecha);
        
        // Convertimos la hora recibida a objeto LocalTime
        LocalTime horaEvaluar = LocalTime.parse(hora);
        
        return horarios.stream().anyMatch(d -> {
            // Convertimos las horas de la base de datos (String) a LocalTime en el momento
            LocalTime inicio = LocalTime.parse(d.getHoraInicio());
            LocalTime fin = LocalTime.parse(d.getHoraFin());
            
            return !horaEvaluar.isBefore(inicio) && !horaEvaluar.isAfter(fin);
        });
    }

    // ── CRUD ─────────────────────────────────────────
    public List<DisponibilidadResponseDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<DisponibilidadResponseDTO> obtenerPorId(Long idDisponibilidad) {
        return repository.findById(idDisponibilidad).map(this::mapToDTO);
    }

    public List<DisponibilidadResponseDTO> buscarPorProfesional(String rutProfesional) {
        return repository.findByRutProfesional(rutProfesional)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<DisponibilidadResponseDTO> buscarPorFecha(String fecha) {
        return repository.findByFecha(fecha)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<DisponibilidadResponseDTO> buscarPorFechayHora(String rutProfesional, String fecha, String horaInicio) {
        return repository.findByRutProfesionalAndFechaAndHoraInicio(rutProfesional, fecha, horaInicio)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public DisponibilidadResponseDTO guardar(DisponibilidadRequestDTO dto) {
        validarProfesional(dto.getRutProfesional());
        DisponibilidadProfesional disp = new DisponibilidadProfesional(
            null, 
            dto.getRutProfesional(), 
            dto.getFecha(),
            dto.getHoraInicio(), 
            dto.getHoraFin());
        return mapToDTO(repository.save(disp));
    }

    public Optional<DisponibilidadResponseDTO> actualizar(Long idDisponibilidad, DisponibilidadRequestDTO dto) {
        return repository.findById(idDisponibilidad).map(existente -> {
            validarProfesional(dto.getRutProfesional());
            existente.setFecha(dto.getFecha());
            existente.setHoraInicio(dto.getHoraInicio());
            existente.setHoraFin(dto.getHoraFin());
            return mapToDTO(repository.save(existente));
        });
    }

    public void eliminar(Long idDisponibilidad) {
        repository.deleteById(idDisponibilidad);
    }

}
