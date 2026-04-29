package com.mx.Doctores.dto;

import com.mx.Doctores.entity.Estatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorRequestDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "La cédula es obligatoria")
    private String cedula;
    
    @NotNull(message = "El teléfono no puede ser nulo")
    private Integer telefono;
    
    @Email(message = "El formato del correo es inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
    @NotNull(message = "El estatus no puede ser nulo")
    private Estatus estatus; 		
}