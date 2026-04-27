package com.mx.Pacientes.dominio;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Table(name="PACIENTES_MS")
@Data
public class Pacientes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private Integer edad;
	private String domicilio;
	@Column(unique = true)
	private String email;
	@Enumerated(EnumType.STRING)
	private Estatus estatus;
	
	
}
