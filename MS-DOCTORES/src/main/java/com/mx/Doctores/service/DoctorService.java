package com.mx.Doctores.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.Doctores.dao.IDoctorDao;
import com.mx.Doctores.dto.DoctorRequestDTO;
import com.mx.Doctores.dto.DoctorResponseDTO;
import com.mx.Doctores.entity.Doctor;
import com.mx.Doctores.entity.Estatus;
import com.mx.Doctores.exception.DoctorNotFoundException;

@Service
public class DoctorService {

    @Autowired
    private IDoctorDao doctorDao;

    @Autowired
    private ModelMapper modelMapper;

    // Listar todos los doctores 
    public List<DoctorResponseDTO> listarTodos() {
        return doctorDao.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }
    
    //Listar Disponibles
    public List<DoctorResponseDTO> listarDisponibles() {
        return doctorDao.findByEstatus(Estatus.DISPONIBLE).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Buscar por ID 
    public DoctorResponseDTO buscarPorId(Integer id) {
        Doctor doctor = doctorDao.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor no encontrado con el ID: " + id));
        return modelMapper.map(doctor, DoctorResponseDTO.class);
    }

    // Guardar un nuevo doctor
    public DoctorResponseDTO guardar(DoctorRequestDTO request) {
    	
    	if (doctorDao.findByCedula(request.getCedula()).isPresent()) {
            throw new RuntimeException("La cédula '" + request.getCedula() + "' ya está registrada.");
        }
        Doctor doctor = modelMapper.map(request, Doctor.class);
        
        if (doctor.getEstatus() == null) {
            doctor.setEstatus(Estatus.DISPONIBLE);
        }
        
        Doctor doctorGuardado = doctorDao.save(doctor);
        return modelMapper.map(doctorGuardado, DoctorResponseDTO.class);
    }

    // Actualizar un 	
    public DoctorResponseDTO actualizar(Integer id, DoctorRequestDTO request) {
        Doctor doctorExistente = doctorDao.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("No se puede actualizar, el ID: " + id + " no existe."));
        
        // Copiamos los datos del RequestDTO a la entidad encontrada
        modelMapper.map(request, doctorExistente);
        doctorExistente.setId(id); // Mantener el ID original
        
        Doctor doctorActualizado = doctorDao.save(doctorExistente);
        return modelMapper.map(doctorActualizado, DoctorResponseDTO.class);
    }

    // Eliminar doctor
    public void eliminar(Integer id) {
        if (!doctorDao.existsById(id)) {
            throw new DoctorNotFoundException("No se puede eliminar, el ID: " + id + " no existe.");
        }
        doctorDao.deleteById(id);
    }
}