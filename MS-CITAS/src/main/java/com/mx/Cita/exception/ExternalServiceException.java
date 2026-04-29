package com.mx.Cita.exception;

public class ExternalServiceException extends RuntimeException {
	
    public ExternalServiceException(String mensaje) {
        super(mensaje);
    }
}