package arso.segundum.rest.exception;

import arso.segundum.rest.dto.ErrorDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(ProductoNoDisponibleException.class)
    public ResponseEntity<ErrorDTO> handleProductoNoDisponible(ProductoNoDisponibleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(ErrorServicioExternoException.class)
    public ResponseEntity<ErrorDTO> handleExternalService(ErrorServicioExternoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ErrorDTO("Errores de validación", errores));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDTO> handleMissingParam(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(new ErrorDTO("Parámetro requerido ausente: " + ex.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensaje = String.format("El parámetro '%s' tiene un valor inválido: '%s'",
                ex.getName(), ex.getValue());
        return ResponseEntity.badRequest().body(new ErrorDTO(mensaje));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errores = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ErrorDTO("Parámetros inválidos", errores));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorDTO(ex.getReason() != null ? ex.getReason() : ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneric(Exception ex) {
        return ResponseEntity.internalServerError().body(new ErrorDTO("Error interno del servidor"));
    }
}