package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;

import java.time.LocalTime;

public record LinhaResponseDto(
		String nomeLinha,
		String numeroLinha,
		TipoTarifa tarifa
) {
}
