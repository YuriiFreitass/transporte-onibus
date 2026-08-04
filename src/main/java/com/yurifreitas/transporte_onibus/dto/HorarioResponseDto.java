package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoDia;

import java.time.LocalTime;

public record HorarioResponseDto(
		Long id,
		LocalTime horario,
		TipoDia dia
) {}
