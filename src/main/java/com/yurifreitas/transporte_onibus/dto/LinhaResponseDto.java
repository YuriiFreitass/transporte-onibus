package com.yurifreitas.transporte_onibus.dto;

import java.time.LocalDateTime;

public record LinhaResponseDto(
		String nomeLinha,
		LocalDateTime horarioLinha
) {
}
