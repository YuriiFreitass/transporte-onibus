package com.yurifreitas.transporte_onibus.client;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;

public record LinhaParaImportar(
		String identificadorPagina,
		TipoTarifa tarifa
) {
}
