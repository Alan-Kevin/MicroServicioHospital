package com.mx.Cita.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    
	private Integer id;
    private String nombre;
    private String especialidad;
    private String estatus; // DISPONIBLE / NO_DISPONIBLE
}