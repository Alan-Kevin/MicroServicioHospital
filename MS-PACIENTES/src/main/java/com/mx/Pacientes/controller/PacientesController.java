package com.mx.Pacientes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mx.Pacientes.dto.PacientesRequestDTO;
import com.mx.Pacientes.service.PacienteService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("pacientes") 
public class PacientesController {

    private final PacienteService service;
    
    public PacientesController(PacienteService service) {
		this.service = service;
	}

	// Listar todos
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // Listar solo pacientes Activos
    @GetMapping("activos")
    public ResponseEntity<?> listarActivos() {
        return ResponseEntity.ok(service.listarActivos());
    }

    // Guardar 
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody PacientesRequestDTO dto) {
        // Usamos status 201 (Created) para creaciones exitosas
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    // Buscar paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Editar 
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable int id, @Valid @RequestBody PacientesRequestDTO dto) {
        return ResponseEntity.ok(service.editar(id, dto));
    }

    // Eliminación 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        service.eliminar(id);
        
        return ResponseEntity.noContent().build();
    }
}