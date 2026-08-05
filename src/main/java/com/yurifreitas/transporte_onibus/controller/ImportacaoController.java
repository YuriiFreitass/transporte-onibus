package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.service.ImportacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/importacoes")
@RequiredArgsConstructor
public class ImportacaoController {

	private final ImportacaoService importacaoService;

	@PostMapping("/{identificadorPagina}/{tarifa}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void importar(
			@PathVariable String identificadorPagina,
			@PathVariable TipoTarifa tarifa
	) {
		importacaoService.importar(identificadorPagina, tarifa);
	}

	@PostMapping("/linhas-e-horarios")
	public int importarCatalogo() {
		return importacaoService.importarCatalogo();
	}
}