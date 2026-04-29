package com.mx.Doctores.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DOCTORES_MS")
@Data
public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    @Column(unique = true, nullable = false)
    private String cedula;
    private Integer telefono;
    @Column(unique = true)
    private String correo;
    @Enumerated(EnumType.STRING)
    private Estatus estatus;
}
