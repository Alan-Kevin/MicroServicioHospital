package com.mx.Cita.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.Cita.entity.Cita;
import com.mx.Cita.entity.EstatusCita;

@Repository
public interface ICitaDao extends JpaRepository<Cita, Integer> {

    List<Cita> findByIdPaciente(Integer idPaciente);

    List<Cita> findByIdDoctorAndEstatus(Integer idDoctor, EstatusCita estatus);
}