package com.mx.Cita.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Cita.dto.CitaRequestDTO;
import com.mx.Cita.dto.CitaResponseDTO;
import com.mx.Cita.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("citas")
@RequiredArgsConstructor
public class CitaController {

 
    private final CitaService citaService;

    // Listar todas las citas 
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listarTodas() {
        List<CitaResponseDTO> citas = citaService.listarTodas();
        return ResponseEntity.ok(citas);
    }

    // Agendar una nueva cita 
    @PostMapping
    public ResponseEntity<CitaResponseDTO> agendar(@Valid @RequestBody CitaRequestDTO request) {
        CitaResponseDTO nuevaCita = citaService.agendarCita(request);
        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }
    
 // Editar cita 
    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> editar(@PathVariable Integer id, @Valid @RequestBody CitaRequestDTO request) {
        return ResponseEntity.ok(citaService.editar(id, request));
    }

    // Cancelar cita 
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        citaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<CitaResponseDTO>> listarPorPaciente(@PathVariable Integer idPaciente) {
        List<CitaResponseDTO> citas = citaService.obtenerCitasPorPaciente(idPaciente);
        
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(citas); 
    }
}