package com.mx.Cita.exception;

public class CitaNotFoundException extends RuntimeException {
	
    public CitaNotFoundException(String mensaje) {
        super(mensaje);
    }
}