package com.mx.Pacientes.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mx.Pacientes.dao.IPacientesDao;
import com.mx.Pacientes.dominio.Pacientes;
import com.mx.Pacientes.dominio.Estatus;
import com.mx.Pacientes.dto.PacientesRequestDTO;
import com.mx.Pacientes.dto.PacientesResponseDTO;
import com.mx.Pacientes.exception.ResourceNotFoundException;


@Service
public class PacienteService {

    private final IPacientesDao dao;
    private final ModelMapper mapper;
    
    public PacienteService(IPacientesDao dao, ModelMapper mapper) {
		this.dao = dao;
		this.mapper = mapper;
	}

	// Listar TODOS
    public List<PacientesResponseDTO> listar() {
        return dao.findAll().stream()
                .map(paciente -> mapper.map(paciente, PacientesResponseDTO.class))
                .toList();
    }

    // Listar ACTIVOS
    public List<PacientesResponseDTO> listarActivos() {
        List<Pacientes> pacientes = dao.findByEstatus(Estatus.ACTIVO);
        return pacientes.stream()
                .map(paciente -> mapper.map(paciente, PacientesResponseDTO.class))
                .toList();
    }

    // GUARDAR
    public PacientesResponseDTO guardar(PacientesRequestDTO dto) {
        // Validación de email único
        dao.findByEmail(dto.getEmail()).ifPresent(p -> {
            throw new RuntimeException("El email del paciente ya existe, intenta con otro.");
        });

        Pacientes paciente = mapper.map(dto, Pacientes.class);
        return mapper.map(dao.save(paciente), PacientesResponseDTO.class);
    }

    // BUSCAR POR ID
    public PacientesResponseDTO buscarPorId(int id) {
        Pacientes paciente = dao.findById(id)
                .orElseThrow(() -> 
                new ResourceNotFoundException("Paciente no encontrado con el id: " + id));

        return mapper.map(paciente, PacientesResponseDTO.class);
    }

    // EDITAR
    public PacientesResponseDTO editar(int id, PacientesRequestDTO dto) {
        Pacientes paciente = dao.findById(id)
                .orElseThrow(() -> 
                new ResourceNotFoundException("Paciente no encontrado con el id: " + id));

        paciente.setNombre(dto.getNombre());
        paciente.setEdad(dto.getEdad());
        paciente.setDomicilio(dto.getDomicilio());
        paciente.setEmail(dto.getEmail());
        paciente.setEstatus(dto.getEstatus());

        return mapper.map(dao.save(paciente), PacientesResponseDTO.class);
    }

    // ELIMINACIÓN LÓGICA
    public void eliminar(int id) {
        Pacientes paciente = dao.findById(id)
                .orElseThrow(() -> 
                new ResourceNotFoundException("Paciente no encontrado con el id: " + id));

        paciente.setEstatus(Estatus.ALTA);
        dao.save(paciente);
    }
    
}