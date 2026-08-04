package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoDia;

import java.time.LocalTime;

public record HorarioRequestDto(
		LocalTime horario,
		TipoDia dia
) {}
