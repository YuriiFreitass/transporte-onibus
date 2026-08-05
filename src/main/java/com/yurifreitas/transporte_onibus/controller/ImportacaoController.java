package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.client.EmpresaVitoriaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.jsoup.nodes.Document;


@RestController
@RequestMapping("/v1/importacoes")
@RequiredArgsConstructor
public class ImportacaoController {

	private final EmpresaVitoriaClient empresaVitoriaClient;

	@GetMapping("/teste/{identificadorPagina}")
	public String testarConexao(
			@PathVariable String identificadorPagina
	) {
		Document document = empresaVitoriaClient.buscarPagina(identificadorPagina);

		return document.title();
	}
}
