package com.mx.Doctores.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mx.Doctores.entity.Doctor;
import com.mx.Doctores.entity.Estatus;

@Repository
public interface IDoctorDao extends JpaRepository<Doctor, Integer> {
    
    List<Doctor> findByEstatus(Estatus estatus);
    
    Optional<Doctor> findByCedula(String cedula);
}