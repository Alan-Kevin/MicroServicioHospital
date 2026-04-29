package com.mx.Cita.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.Cita.dao.ICitaDao;
import com.mx.Cita.dto.CitaRequestDTO;
import com.mx.Cita.dto.CitaResponseDTO;
import com.mx.Cita.dto.DoctorDTO;
import com.mx.Cita.dto.PacienteDTO;
import com.mx.Cita.entity.Cita;
import com.mx.Cita.entity.DetalleCita;
import com.mx.Cita.entity.EstatusCita;
import com.mx.Cita.exception.CitaNotFoundException;
import com.mx.Cita.exception.ExternalServiceException;
import com.mx.Cita.feign.IDoctorClient;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

	private final ICitaDao citaDao;
	private final IDoctorClient doctorClient;
	private final RestTemplate restTemplate;
	private final ModelMapper modelMapper;

	private final String URL_PACIENTES = "http://localhost:8080/pacientes/";

	@Transactional
	public CitaResponseDTO agendarCita(CitaRequestDTO request) {

		// Validar Paciente vía RestTemplate
		PacienteDTO paciente;
		try {
			paciente = restTemplate.getForObject(URL_PACIENTES + request.getIdPaciente(), PacienteDTO.class);
			if (paciente == null) {
				throw new ExternalServiceException("Error: El paciente con ID " + request.getIdPaciente() + " no existe.");
			}
		} catch (Exception e) {
			throw new ExternalServiceException("Error al conectar con el microservicio de Pacientes o Paciente no encontrado.");
		}

		// Validar Doctor vía Feign
		DoctorDTO doctor;
		try {
			doctor = doctorClient.buscarPorId(request.getIdDoctor());

			if (doctor == null) {
				throw new ExternalServiceException("Error: El doctor con ID " + request.getIdDoctor() + " no existe.");
			}

			// El doctor debe estar DISPONIBLE para agendar 
			if (!"DISPONIBLE".equalsIgnoreCase(doctor.getEstatus())) {
				throw new ExternalServiceException("El doctor con ID " + request.getIdDoctor() + " no está disponible (Estatus: " + doctor.getEstatus() + ").");
			}
		} catch (Exception e) {
			throw new ExternalServiceException("Error: El doctor con ID " + request.getIdDoctor() + " ya no se encuentra disponible.");
		}

		//2 citas por dia
		
		// Recuperamos citas PROGRAMADAS del doctor 
		List<Cita> citasActivas = citaDao.findByIdDoctorAndEstatus(request.getIdDoctor(), EstatusCita.PROGRAMADA);

		// Filtramos por el mismo día 
		long citasDelMismoDia = citasActivas.stream()
				.filter(c -> c.getFechaProgramada().toLocalDate().equals(request.getFechaProgramada().toLocalDate()))
				.count();

		if (citasDelMismoDia >= 2) {
			throw new ExternalServiceException("El doctor con ID " + request.getIdDoctor() + " ya tiene el cupo lleno de 2 citas por día).");
		}

		//Crear y guardar la Cita
		Cita cita = new Cita();
		cita.setIdPaciente(paciente.getId());
		cita.setIdDoctor(doctor.getId());
		cita.setFechaProgramada(request.getFechaProgramada());
		cita.setFechaRegistro(LocalDateTime.now());
		cita.setEstatus(EstatusCita.PROGRAMADA);

		List<DetalleCita> detalles = request.getDetalles().stream().map(detalleDTO -> {
			DetalleCita detalle = modelMapper.map(detalleDTO, DetalleCita.class);
			detalle.setCita(cita);
			return detalle;
		}).collect(Collectors.toList());

		cita.setDetalles(detalles);

		Cita citaGuardada = citaDao.save(cita);

		//Si con esta cita llegamos a 2, marcamos al doctor como NO_DISPONIBLE
		if (citasDelMismoDia + 1 == 2) {
			try {
				doctorClient.actualizarEstatus(request.getIdDoctor(), "NO_DISPONIBLE");
				System.out.println(" Doctor " + request.getIdDoctor() + " marcado automáticamente como NO_DISPONIBLE.");
			} catch (Exception e) {
				System.err.println(" No se pudo actualizar el estatus del doctor: " + e.getMessage());
			}
		}

		return modelMapper.map(citaGuardada, CitaResponseDTO.class);
	}

	public List<CitaResponseDTO> listarTodas() {
		return citaDao.findAll().stream()
				.map(cita -> modelMapper.map(cita, CitaResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public CitaResponseDTO editar(Integer id, CitaRequestDTO request) {
		Cita citaExistente = citaDao.findById(id)
				.orElseThrow(() -> new CitaNotFoundException("No se encontró la cita con ID: " + id));

		citaExistente.setIdPaciente(request.getIdPaciente());
		citaExistente.setIdDoctor(request.getIdDoctor());
		citaExistente.setFechaProgramada(request.getFechaProgramada());

		citaExistente.getDetalles().clear();

		if (request.getDetalles() != null) {
			List<DetalleCita> nuevosDetalles = request.getDetalles().stream().map(detalleDTO -> {
				DetalleCita detalle = modelMapper.map(detalleDTO, DetalleCita.class);
				detalle.setCita(citaExistente);
				return detalle;
			}).collect(Collectors.toList());

			citaExistente.getDetalles().addAll(nuevosDetalles);
		}

		Cita citaActualizada = citaDao.save(citaExistente);
		return modelMapper.map(citaActualizada, CitaResponseDTO.class);
	}

	@Transactional
	public void cancelar(Integer id) {
		Cita cita = citaDao.findById(id)
				.orElseThrow(() -> new CitaNotFoundException("No se puede cancelar: Cita no encontrada con ID: " + id));

		cita.setEstatus(EstatusCita.CANCELADA);
		citaDao.save(cita);
	}
	
	@Transactional
	public List<CitaResponseDTO> obtenerCitasPorPaciente(Integer idPaciente) {
	   
	    List<Cita> citas = citaDao.findByIdPaciente(idPaciente);
	    
	    return citas.stream()
	            .map(cita -> modelMapper.map(cita, CitaResponseDTO.class))
	            .collect(Collectors.toList());
	}
}