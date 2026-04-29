package com.mx.Cita.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.mx.Cita.exception.ErrorResponse;


@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Errores de Validación 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Error de validación en la cita",
                errores
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Error cuando el Paciente o Doctor no existen 
    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> manejarServiciosExternos(ExternalServiceException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Error Interno del servidor"
                
        );
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_CONTENT); // 422
    }
    

    // 3. Error cuando la Cita misma no existe
    @ExceptionHandler(CitaNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarCitaNotFound(CitaNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 4. Errores Globales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErroresGlobales(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Error interno en el microservicio de Citas",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}