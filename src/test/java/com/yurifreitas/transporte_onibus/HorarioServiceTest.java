package com.yurifreitas.transporte_onibus;

import com.yurifreitas.transporte_onibus.dto.HorarioRequestDto;
import com.yurifreitas.transporte_onibus.dto.HorarioResponseDto;
import com.yurifreitas.transporte_onibus.entity.HorarioEntity;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoDia;
import com.yurifreitas.transporte_onibus.exception.LinhaNaoEncontradaException;
import com.yurifreitas.transporte_onibus.mapper.HorarioMapper;
import com.yurifreitas.transporte_onibus.repository.HorarioRepository;
import com.yurifreitas.transporte_onibus.repository.LinhaRepository;
import com.yurifreitas.transporte_onibus.service.HorarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HorarioServiceTest {

	@Mock
	private HorarioRepository horarioRepository;

	@Mock
	private LinhaRepository linhaRepository;

	@Mock
	private HorarioMapper horarioMapper;

	@InjectMocks
	private HorarioService horarioService;

	@Test // Teste de sucesso
	void deveRetornarHorariosQuandoLinhaExistir() {

		String numeroLinha = "205";

		HorarioEntity horarioEntity = new HorarioEntity();

		HorarioResponseDto responseDto = new HorarioResponseDto(
				"205",
				LocalTime.of(4,20),
				TipoDia.DIAS_UTEIS,
				"Metrópole");

		when(linhaRepository.existsByNumeroLinha(numeroLinha)).thenReturn(true);

		when(horarioRepository.findByLinha_NumeroLinhaOrderByHorarioAsc(numeroLinha))
				.thenReturn(List.of(horarioEntity));

		when(horarioMapper.toResponseDto(horarioEntity)).thenReturn(responseDto);

		List<HorarioResponseDto> resultado = horarioService.findByNumeroLinha(numeroLinha);

		assertFalse(resultado.isEmpty());
		assertEquals(1, resultado.size());
		assertEquals("205", resultado.getFirst().numeroLinha());
		assertEquals(LocalTime.of(4,20), resultado.getFirst().horario());

		verify(linhaRepository).existsByNumeroLinha(numeroLinha);
		verify(horarioRepository).findByLinha_NumeroLinhaOrderByHorarioAsc(numeroLinha);
		verify(horarioMapper).toResponseDto(horarioEntity);
	}

	@Test // Teste de erro
	void deveLancarExceptionQuandoLinhaNaoExistir() {

		String numeroLinha = "999";

		when(linhaRepository.existsByNumeroLinha(numeroLinha)).thenReturn(false);

		assertThrows(LinhaNaoEncontradaException.class,
				() -> horarioService.findByNumeroLinha(numeroLinha));

		verify(linhaRepository).existsByNumeroLinha(numeroLinha);
		verifyNoInteractions(horarioRepository, horarioMapper);
	}

	@Test
	void deveSubstituirHorariosDaLinha() {

		LinhaEntity linha = new LinhaEntity();
		linha.setNumeroLinha("205");

		HorarioRequestDto requestDto = new HorarioRequestDto("205",
				LocalTime.of(4,20),
				TipoDia.DIAS_UTEIS,
				"Metrópole");

		HorarioEntity horarioEntity = new HorarioEntity();

		when(horarioMapper.toEntity(requestDto)).thenReturn(horarioEntity);

		horarioService.substituirHorarios(linha, List.of(requestDto));

		verify(horarioRepository).deleteByLinha_NumeroLinha("205");

		verify(horarioMapper).toEntity(requestDto);

		verify(horarioRepository).saveAll(List.of(horarioEntity));

		assertEquals(linha, horarioEntity.getLinha());
	}
}
