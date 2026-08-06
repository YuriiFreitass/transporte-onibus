package com.yurifreitas.transporte_onibus.client;

import com.yurifreitas.transporte_onibus.dto.HorarioRequestDto;
import com.yurifreitas.transporte_onibus.dto.LinhaRequestDto;
import com.yurifreitas.transporte_onibus.enums.TipoDia;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.exception.DadosEmpresaVitoriaInvalidosException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmpresaVitoriaParser {

	private static final DateTimeFormatter FORMATADOR_HORARIO =
			DateTimeFormatter.ofPattern("H:mm");

	public LinhaRequestDto extrairLinha(
			Document document,
			TipoTarifa tarifa
	) {
		Element titulo = document.selectFirst("h3");

		if (titulo == null) {
			throw new DadosEmpresaVitoriaInvalidosException(
					"Título da linha não encontrado."
			);
		}

		String[] partes = titulo.text()
				.trim()
				.split("\\s+-\\s+", 2);

		if (partes.length != 2) {
			throw new DadosEmpresaVitoriaInvalidosException(
					"Formato inesperado do título da linha."
			);
		}

		return new LinhaRequestDto(
				partes[0].trim(),
				partes[1].trim(),
				tarifa
		);
	}

	public List<HorarioRequestDto> extrairHorarios(
			Document document,
			String numeroLinha
	) {
		List<HorarioRequestDto> horarios = new ArrayList<>();

		for (Element tabela : document.select("table")) {
			Elements linhas = tabela.select("tr");

			if (linhas.size() < 3) {
				continue;
			}

			Elements locaisPartida = linhas.get(1).select("th");

			if (locaisPartida.size() < 2) {
				continue;
			}

			TipoDia dia = converterTipoDia(
					linhas.get(0).text()
			);

			String primeiroLocal =
					locaisPartida.get(0).text().trim();

			String segundoLocal =
					locaisPartida.get(1).text().trim();

			for (int i = 2; i < linhas.size(); i++) {
				Elements celulas = linhas.get(i).select("td");

				if (celulas.size() < 3) {
					continue;
				}

				adicionarHorario(
						horarios,
						numeroLinha,
						celulas.get(0).text(),
						dia,
						primeiroLocal
				);

				adicionarHorario(
						horarios,
						numeroLinha,
						celulas.get(2).text(),
						dia,
						segundoLocal
				);
			}
		}

		return horarios;
	}

	public List<LinhaParaImportar> extrairCatalogo(
			Document document
	) {
		return document.select("div.filtr-item")
				.stream()
				.map(this::converterLinha)
				.toList();
	}

	private void adicionarHorario(
			List<HorarioRequestDto> horarios,
			String numeroLinha,
			String textoHorario,
			TipoDia dia,
			String localPartida
	) {
		String valor = textoHorario.trim();

		if (!valor.matches("\\d{1,2}:\\d{2}")) {
			return;
		}

		horarios.add(
				new HorarioRequestDto(
						numeroLinha,
						LocalTime.parse(
								valor,
								FORMATADOR_HORARIO
						),
						dia,
						localPartida
				)
		);
	}

	private TipoDia converterTipoDia(String texto) {
		String tipoDiaNormalizado = normalizarTexto(texto);

		return switch (tipoDiaNormalizado) {
			case "DIAS UTEIS",
			     "SEGUNDA A SEXTA",
			     "SEGUNDA QUARTA SEXTA",
			     "SEG, QUA E SEXTA",
			     "TERCA E QUINTA" ->
					TipoDia.DIAS_UTEIS;

			case "SABADO" ->
					TipoDia.SABADO;

			case "DOMINGO" ->
					TipoDia.DOMINGO;

			default -> throw new DadosEmpresaVitoriaInvalidosException(
					"Tipo de dia desconhecido: [" + texto +
							"] | Normalizado: [" +
							tipoDiaNormalizado + "]"
			);
		};
	}

	private LinhaParaImportar converterLinha(Element card) {
		Element link = card.selectFirst("a");

		if (link == null) {
			throw new DadosEmpresaVitoriaInvalidosException(
					"Link da linha não encontrado."
			);
		}

		String href = link.attr("href");

		if (href.isBlank() || !href.contains("/")) {
			throw new DadosEmpresaVitoriaInvalidosException(
					"Link da linha possui formato inválido: " + href
			);
		}

		String identificadorPagina =
				href.substring(href.lastIndexOf("/") + 1);

		TipoTarifa tarifa =
				converterTarifa(card.attr("data-sort"));

		return new LinhaParaImportar(
				identificadorPagina,
				tarifa
		);
	}

	private TipoTarifa converterTarifa(String tipo) {
		return switch (tipo.trim()) {
			case "Metropolitanas" ->
					TipoTarifa.METROPOLITANA;

			case "Urbanas Tarifa Zero" ->
					TipoTarifa.URBANA_ZERO;

			default -> throw new DadosEmpresaVitoriaInvalidosException(
					"Tipo de tarifa inválido: " + tipo
			);
		};
	}

	private String normalizarTexto(String texto) {
		return Normalizer
				.normalize(texto, Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "")
				.replace('\u00A0', ' ')
				.replaceAll("\\s+", " ")
				.strip()
				.toUpperCase();
	}
}