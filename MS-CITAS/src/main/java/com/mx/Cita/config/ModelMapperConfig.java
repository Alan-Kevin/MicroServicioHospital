package com.mx.Cita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}