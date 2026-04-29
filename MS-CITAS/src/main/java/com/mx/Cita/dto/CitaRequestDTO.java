package com.mx.Cita.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CitaRequestDTO {
    
    @NotNull(message = "El id del paciente es obligatorio.")
    private Integer idPaciente;
    
    @NotNull(message = "El id del doctor es obligatorio.")
    private Integer idDoctor;
    
    @NotNull(message = "La fecha de la cita es obligatoria.")
    private LocalDateTime fechaProgramada;

    @NotNull(message = "Debe incluir al menos un detalle médico.")
    private List<DetalleCitaDTO> detalles;
}