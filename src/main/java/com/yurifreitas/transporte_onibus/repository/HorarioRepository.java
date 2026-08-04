package com.yurifreitas.transporte_onibus.repository;

import com.yurifreitas.transporte_onibus.entity.HorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface HorarioRepository extends JpaRepository<HorarioEntity, Long> {

	LocalTime findHorario(LocalTime horario);
}
