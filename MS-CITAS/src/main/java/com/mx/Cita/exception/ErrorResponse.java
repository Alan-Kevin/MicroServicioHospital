package com.mx.Cita.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
   
	private LocalDateTime timestamp;
    private String mensaje;
    private String detalles;
}