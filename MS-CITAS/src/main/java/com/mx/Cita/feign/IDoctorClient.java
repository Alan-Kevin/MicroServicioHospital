package com.mx.Cita.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mx.Cita.dto.DoctorDTO;

@FeignClient(name = "MS-DOCTORES")
public interface IDoctorClient {

    @GetMapping("doctores/{id}")
    DoctorDTO buscarPorId(@PathVariable("id") Integer id);
    
    @PostMapping("/doctores/{id}/estatus")
    void actualizarEstatus(@PathVariable("id") Integer id, @RequestParam("estatus") String estatus);

}