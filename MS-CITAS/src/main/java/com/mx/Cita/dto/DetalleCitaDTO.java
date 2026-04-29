package com.mx.Cita.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "padecimiento", "diagnostico", "notas"})
public class DetalleCitaDTO {
	
    private Integer id;
    private String padecimiento;
    private String diagnostico;
    private String notas;
}