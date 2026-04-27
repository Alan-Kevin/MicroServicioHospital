package com.mx.Pacientes.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

	@Data
	@JsonPropertyOrder({"id","nombre","edad","domicilio","email","estatus"})
	public class PacientesResponseDTO {
		private Integer id;
		private String nombre;
		private Integer edad;
		private String domicilio;
		private String email;
		private String estatus;
	}
