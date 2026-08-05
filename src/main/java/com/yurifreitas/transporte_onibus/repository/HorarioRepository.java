package com.yurifreitas.transporte_onibus.repository;

import com.yurifreitas.transporte_onibus.entity.HorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface HorarioRepository extends JpaRepository<HorarioEntity, Long> {

	// Encontra o número da linha por horario ascendente. (String numeroLinha somente como parâmetro de busca)
	List<HorarioEntity> findByLinha_NumeroLinhaOrderByHorarioAsc(String numeroLinha);
}
