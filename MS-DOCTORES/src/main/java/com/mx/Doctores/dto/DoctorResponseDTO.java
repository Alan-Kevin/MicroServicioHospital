package com.mx.Doctores.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre","cedula","telefono","email","estatus"})
public class DoctorResponseDTO {
	
    private Integer id;
    private String nombre;
    private String cedula;
    private Integer telefono;
    private String correo;
    private String estatus;
    
}