package com.yurifreitas.transporte_onibus.service;

import com.yurifreitas.transporte_onibus.dto.LinhaRequestDto;
import com.yurifreitas.transporte_onibus.dto.LinhaResponseDto;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.mapper.LinhaMapper;
import com.yurifreitas.transporte_onibus.repository.LinhaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LinhaServiceTest {

	@Mock
	private LinhaRepository linhaRepository;

	@Mock
	private LinhaMapper linhaMapper;

	@InjectMocks
	private LinhaService linhaService;

	@Test
	void deveRetornarTodasAsLinhas() {

		Pageable pageable = PageRequest.of(0,10);

		LinhaEntity linhaEntity = new LinhaEntity();

		LinhaResponseDto responseDto = new LinhaResponseDto(
				"Terminal Metrópole",
				"205",
				TipoTarifa.METROPOLITANA
		);

		Page<LinhaEntity> pagina = new PageImpl<>(List.of(linhaEntity));

		when(linhaRepository.findAll(pageable)).thenReturn(pagina);

		when(linhaMapper.toResponseDto(linhaEntity)).thenReturn(responseDto);

		Page<LinhaResponseDto> resultado = linhaService.findAll(pageable);

		assertFalse(resultado.isEmpty());
		assertEquals(1, resultado.getTotalElements());
		assertEquals("205", resultado.getContent().getFirst().numeroLinha());

		verify(linhaRepository).findAll(pageable);

		verify(linhaMapper).toResponseDto(linhaEntity);
	}

	@Test
	void deveRetornarLinhasPorTarifa() {

		TipoTarifa tarifa = TipoTarifa.METROPOLITANA;

		Pageable pageable = PageRequest.of(0,10);

		LinhaEntity linhaEntity = new LinhaEntity();

		LinhaResponseDto responseDto = new LinhaResponseDto(
			"Terminal Metrópole",
				"205",
				TipoTarifa.METROPOLITANA
		);

		Page<LinhaEntity> pagina = new PageImpl<>(List.of(linhaEntity));

		when(linhaRepository.findByTarifa(tarifa, pageable)).thenReturn(pagina);

		when(linhaMapper.toResponseDto(linhaEntity)).thenReturn(responseDto);

		Page<LinhaResponseDto> resultado = linhaService.findByTarifa(tarifa, pageable);

		assertFalse(resultado.isEmpty());
		assertEquals(TipoTarifa.METROPOLITANA, resultado.getContent().getFirst().tarifa());

		verify(linhaRepository).findByTarifa(tarifa, pageable);

		verify(linhaMapper).toResponseDto(linhaEntity);
	}

	@Test
	void deveRetornarLinhaQuandoJaExistir() {

		LinhaRequestDto requestDto = new LinhaRequestDto(
				"205",
				"Terminal Metrópole",
				TipoTarifa.METROPOLITANA

		);

		LinhaEntity linhaExistente = new LinhaEntity();

		linhaExistente.setNumeroLinha("205");
		linhaExistente.setNomeLinha("Terminal Metrópole");
		linhaExistente.setTarifa(TipoTarifa.METROPOLITANA);

		when(linhaRepository.findByNumeroLinha("205")).thenReturn(Optional.of(linhaExistente));

		LinhaEntity resultado = linhaService.salvarOuBuscarExistente(requestDto);

		assertSame(linhaExistente, resultado);

		verify(linhaRepository).findByNumeroLinha("205");

		verifyNoInteractions(linhaMapper);

		verify(linhaRepository, never()).save(any());
	}

	@Test
	void deveSalvarLinhaQuandoNaoExistir() {

		LinhaRequestDto requestDto = new LinhaRequestDto(
				"205",
				"Terminal Metrópole",
				TipoTarifa.METROPOLITANA
		);

		LinhaEntity linhaEntity = new LinhaEntity();

		LinhaEntity linhaSalva = new LinhaEntity();

		when(linhaRepository.findByNumeroLinha("205")).thenReturn(Optional.empty());

		when(linhaMapper.toEntity(requestDto)).thenReturn(linhaEntity);

		when(linhaRepository.save(linhaEntity)).thenReturn(linhaSalva);

		LinhaEntity resultado = linhaService.salvarOuBuscarExistente(requestDto);

		assertSame(linhaSalva, resultado);

		verify(linhaRepository).findByNumeroLinha("205");

		verify(linhaMapper).toEntity(requestDto);

		verify(linhaRepository).save(linhaEntity);
	}
}
