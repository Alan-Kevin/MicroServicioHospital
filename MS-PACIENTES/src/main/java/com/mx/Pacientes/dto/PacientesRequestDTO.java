package com.mx.Pacientes.dto;

import com.mx.Pacientes.dominio.Estatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PacientesRequestDTO {
	
	@NotBlank(message="El nombre es un campo obligatorio")
	private String nombre;
	@NotNull(message="La edad es un campo obligatorio")
	private Integer edad;
	@NotBlank(message="La direccion es un campo obligatorio")
	private String domicilio;
	@NotBlank(message="El email es un campo obligatorio")
	@Email(message="Email no valido")
	private String email;
	@NotNull
	private Estatus estatus;
	
}
