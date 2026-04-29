package com.mx.Cita.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "idPaciente", "idDoctor", "fechaProgramada", "estatus", "detalles"})
public class CitaResponseDTO {

    private Integer id;
    private Integer idPaciente;
    private Integer idDoctor;
    private LocalDateTime fechaProgramada;
    private String estatus;
    private List<DetalleCitaDTO> detalles;

}