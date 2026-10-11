package com.peluqueria.agenda.Exception;

/*Manejador Global de Excepciones. Su función principal es "interceptar" 
los errores que ocurren en cualquier parte de tu aplicación 
(especialmente en los controladores) y transformarlos en una respuesta 
JSON limpia y organizada para el cliente
*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
 
import java.util.LinkedHashMap;
import java.util.Map;
 
@Slf4j
@RestControllerAdvice
public class GlobalException {
 
    // 400: datos inválidos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
 
    // 404: cliente o tipo de servicio inexistente
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNoEncontrado(RecursoNoEncontradoException ex) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage());
    }
 
    // 409: horario ocupado o profesional sin disponibilidad
    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<Map<String, String>> handleConflicto(ConflictoException ex) {
        return construir(HttpStatus.CONFLICT, ex.getMessage());
    }
 
    // 503: otro microservicio caído o con error
    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ResponseEntity<Map<String, String>> handleServicioNoDisponible(ServicioNoDisponibleException ex) {
        log.error(">>> Servicio externo no disponible: {}", ex.getMessage());
        return construir(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }
 
    // 500: cualquier otro error interno inesperado
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        log.error(">>> Error interno", ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }
 
    private ResponseEntity<Map<String, String>> construir(HttpStatus status, String mensaje) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", mensaje);
        return ResponseEntity.status(status).body(error);
    }
}
