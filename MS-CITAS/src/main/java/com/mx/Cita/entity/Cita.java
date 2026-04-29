package com.mx.Cita.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "CITAS_MS")
@Data
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idPaciente;
    private Integer idDoctor;

    private LocalDateTime fechaProgramada; // La fecha que elige el usuario
    private LocalDateTime fechaRegistro;   

    @Enumerated(EnumType.STRING)
    private EstatusCita estatus;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DetalleCita> detalles;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        this.estatus = EstatusCita.PROGRAMADA;
    }
}