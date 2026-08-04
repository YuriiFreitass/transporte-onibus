package com.yurifreitas.transporte_onibus.dto;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;

public record LinhaRequestDto(

		String nomeLinha,
		TipoTarifa tarifa

) {}
