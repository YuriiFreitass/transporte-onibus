package com.yurifreitas.transporte_onibus.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmpresaVitoriaClient {

	private static final String BASE_URL =
			"https://www.evitoria.com.br/linhas-e-horarios/";

	private static final String CATALOGO_URL =
			"https://www.evitoria.com.br/linhas-e-horarios";

	public Document buscarPagina(String identificadorPagina) {
		return buscarDocumento(BASE_URL + identificadorPagina);
	}

	public Document buscarCatalogo() {
		return buscarDocumento(CATALOGO_URL);
	}

	private Document buscarDocumento(String url) {
		try {
			return Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
									"AppleWebKit/537.36 (KHTML, like Gecko) " +
									"Chrome/151.0.0.0 Safari/537.36"
					)
					.header(
							"Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9," +
									"image/avif,image/webp,image/apng,*/*;q=0.8"
					)
					.header("Accept-Language", "pt-BR,pt;q=0.9,en;q=0.8")
					.referrer("https://www.google.com/")
					.timeout(15_000)
					.followRedirects(true)
					.get();

		} catch (IOException exception) {
			throw new IllegalStateException(
					"Não foi possível acessar a página da Empresa Vitória.",
					exception
			);
		}
	}
}