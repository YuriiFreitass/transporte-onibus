package com.yurifreitas.transporte_onibus.service;

import com.yurifreitas.transporte_onibus.dto.HorarioRequestDto;
import com.yurifreitas.transporte_onibus.dto.HorarioResponseDto;
import com.yurifreitas.transporte_onibus.entity.HorarioEntity;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.exception.LinhaNaoEncontradaException;
import com.yurifreitas.transporte_onibus.mapper.HorarioMapper;
import com.yurifreitas.transporte_onibus.repository.HorarioRepository;
import com.yurifreitas.transporte_onibus.repository.LinhaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioService {

	private final HorarioRepository horarioRepository;
	private final HorarioMapper horarioMapper;
	private final LinhaRepository linhaRepository;

	public List<HorarioResponseDto> findByNumeroLinha(String numeroLinha) {

		if (!linhaRepository.existsByNumeroLinha(numeroLinha)) {
			throw new LinhaNaoEncontradaException("Linha não encontrada: " + numeroLinha);
		}
		return horarioRepository
				.findByLinha_NumeroLinhaOrderByHorarioAsc(numeroLinha)
				.stream()
				.map(horarioMapper::toResponseDto)
				.toList();
	}

	@Transactional
	public void substituirHorarios(
			LinhaEntity linha,
			List<HorarioRequestDto> requestDtos
	) {
		horarioRepository.deleteByLinha_NumeroLinha(
				linha.getNumeroLinha()
		);

		List<HorarioEntity> horarios = requestDtos.stream()
				.map(requestDto -> {
					HorarioEntity horario =
							horarioMapper.toEntity(requestDto);

					horario.setLinha(linha);

					return horario;
				})
				.toList();

		horarioRepository.saveAll(horarios);
	}
}