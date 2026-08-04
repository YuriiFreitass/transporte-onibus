package com.yurifreitas.transporte_onibus.repository;

import com.yurifreitas.transporte_onibus.entity.HorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface HorarioRepository extends JpaRepository<HorarioEntity, Long> {

	LocalTime findHorario(LocalTime horario);

	// Encontra o número da linha por horario ascendente. (String numeroLinha somente por parâmetro de busca)
	List<HorarioEntity> findByNumeroLinhaOrderByHorarioAsc(String numeroLinha);
}
