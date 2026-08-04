package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;

import java.time.LocalDateTime;

public record LinhaResponseDto(
		String nomeLinha,
		LocalDateTime horarioLinha,
		TipoTarifa tipoTarifa
) {
}
