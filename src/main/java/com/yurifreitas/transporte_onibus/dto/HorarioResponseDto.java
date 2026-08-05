package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoDia;

import java.time.LocalTime;

public record HorarioResponseDto(
		String numeroLinha,
		LocalTime horario,
		TipoDia dia,
		String localPartida
) {}
