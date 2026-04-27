package com.mx.Pacientes.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.Pacientes.dominio.Estatus;
import com.mx.Pacientes.dominio.Pacientes;

public interface IPacientesDao extends JpaRepository<Pacientes, Integer>{
	Optional <Pacientes> findByEmail(String email);
	
	List<Pacientes> findByEstatus(Estatus estatus);

}
