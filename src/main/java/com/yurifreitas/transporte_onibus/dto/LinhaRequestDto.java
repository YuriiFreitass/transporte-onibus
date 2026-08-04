package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LinhaRequestDto(
		@NotBlank
		String numeroLinha,
		@NotBlank
		String nomeLinha,
		@NotNull
		TipoTarifa tarifa

) {}
