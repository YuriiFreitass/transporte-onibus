package com.yurifreitas.transporte_onibus.service;

import com.yurifreitas.transporte_onibus.dto.HorarioResponseDto;
import com.yurifreitas.transporte_onibus.mapper.HorarioMapper;
import com.yurifreitas.transporte_onibus.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioService {

	private final HorarioRepository horarioRepository;
	private final HorarioMapper horarioMapper;

	public List<HorarioResponseDto> findByNumeroLinha(String numeroLinha) {
		return horarioRepository.findByNumeroLinhaOrderByHorarioAsc(numeroLinha)
				.stream()
				.map(horarioMapper::toResponseDto)
				.toList();
	}

}
