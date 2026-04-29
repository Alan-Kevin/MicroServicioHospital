package com.mx.Cita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DETALLE_CITAS_MS")
@Data
public class DetalleCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String padecimiento;
    private String diagnostico;
    private String notas;

    @ManyToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;
}