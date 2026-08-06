package com.yurifreitas.transporte_onibus.dto;

import java.util.List;

public record ErrorResponseDto(
		int status,
		String mensagem,
		List<CampoErrorDto> campos
) {
}
