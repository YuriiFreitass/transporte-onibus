package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoDia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record HorarioRequestDto(
		@NotBlank
		String numeroLinha,
		@NotNull
		LocalTime horario,
		@NotNull
		TipoDia dia
) {}
