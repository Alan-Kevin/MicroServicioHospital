package com.mx.Doctores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Doctores.dto.DoctorRequestDTO;
import com.mx.Doctores.dto.DoctorResponseDTO;
import com.mx.Doctores.service.DoctorService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("doctores")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Listar todos los doctores
    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> listar() {
        return ResponseEntity.ok(doctorService.listarTodos());
    }
    
    //Listar disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<DoctorResponseDTO>> listarDisponibles() {
        return ResponseEntity.ok(doctorService.listarDisponibles());
    }

    // Obtener un doctor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(doctorService.buscarPorId(id));
    }

    // Crear un nuevo doctor 
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> guardar(@Valid @RequestBody DoctorRequestDTO request) {
        return new ResponseEntity<>(doctorService.guardar(request), HttpStatus.CREATED);
    }

    // Actualizar un doctor 
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody DoctorRequestDTO request) {
        return ResponseEntity.ok(doctorService.actualizar(id, request));
    }

    // Eliminar un doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        doctorService.eliminar(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }
    

    @PostMapping("/{id}/estatus")
    public ResponseEntity<Void> actualizarEstatus(@PathVariable Integer id, @RequestParam String estatus) {
        doctorService.actualizarEstatus(id, estatus);
        return ResponseEntity.ok().build();
    }
    
}