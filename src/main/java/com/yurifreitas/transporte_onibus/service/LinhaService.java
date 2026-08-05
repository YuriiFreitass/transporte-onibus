package com.yurifreitas.transporte_onibus.service;

import com.yurifreitas.transporte_onibus.dto.LinhaResponseDto;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.mapper.LinhaMapper;
import com.yurifreitas.transporte_onibus.repository.LinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinhaService {

	private final LinhaRepository linhaRepository;
	private final LinhaMapper linhaMapper;

	// Lista todas as linhas disponíveis
	public Page<LinhaResponseDto> findAll(Pageable pageable) {
		return linhaRepository.findAll(pageable).map(linhaMapper::toResponseDto);
	}

	// Lista todas as linhas por tipo de tarifa
	public Page<LinhaResponseDto> findByTarifa(TipoTarifa tarifa, Pageable pageable) {
		return linhaRepository.findByTarifa(tarifa, pageable).map(linhaMapper::toResponseDto);
	}
}
