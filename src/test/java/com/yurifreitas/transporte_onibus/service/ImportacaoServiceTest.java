package com.yurifreitas.transporte_onibus.service;

import com.yurifreitas.transporte_onibus.client.EmpresaVitoriaClient;
import com.yurifreitas.transporte_onibus.client.EmpresaVitoriaParser;
import com.yurifreitas.transporte_onibus.client.LinhaParaImportar;
import com.yurifreitas.transporte_onibus.dto.HorarioRequestDto;
import com.yurifreitas.transporte_onibus.dto.LinhaRequestDto;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoDia;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImportacaoServiceTest {

	@Mock
	private EmpresaVitoriaClient empresaVitoriaClient;

	@Mock
	private EmpresaVitoriaParser empresaVitoriaParser;

	@Mock
	private LinhaService linhaService;

	@Mock
	private HorarioService horarioService;

	@InjectMocks
	private ImportacaoService importacaoService;

	@Test
	void deveImportarUmaLinhaComSucesso() {

		String identificadorPagina = "205_11-terminal-metropole";

		TipoTarifa tarifa = TipoTarifa.METROPOLITANA;

		Document document = new Document("");

		LinhaRequestDto linhaRequest = new LinhaRequestDto(
				"205",
				"Terminal Metrópole",
				TipoTarifa.METROPOLITANA
		);

		LinhaEntity linhaEntity = new LinhaEntity();

		HorarioRequestDto horarioRequest = new HorarioRequestDto(
				"205",
				LocalTime.of(4,20),
				TipoDia.DIAS_UTEIS,
				"Metrópole"
		);

		List<HorarioRequestDto> horarios = List.of(horarioRequest);

		when(empresaVitoriaClient.buscarPagina(identificadorPagina))
				.thenReturn(document);

		when(empresaVitoriaParser.extrairLinha(document,tarifa))
				.thenReturn(linhaRequest);

		when(linhaService.salvarOuBuscarExistente(linhaRequest))
				.thenReturn(linhaEntity);

		when(empresaVitoriaParser.extrairHorarios(document, "205"))
				.thenReturn(horarios);

		importacaoService.importar(identificadorPagina, tarifa);

		verify(empresaVitoriaClient).buscarPagina(identificadorPagina);

		verify(empresaVitoriaParser).extrairLinha(document, tarifa);

		verify(linhaService).salvarOuBuscarExistente(linhaRequest);

		verify(empresaVitoriaParser).extrairHorarios(document, "205");

		verify(horarioService).substituirHorarios(linhaEntity, horarios);

	}

	@Test
	void deveImportarCatalogoComSucesso() {

		Document catalogo = new Document("");

		LinhaParaImportar primeiraLinha = new LinhaParaImportar(
				"205_11-terminal-metropole",
				TipoTarifa.METROPOLITANA
		);

		LinhaParaImportar segundaLinha = new LinhaParaImportar(
				"linha-002",
				TipoTarifa.URBANA_ZERO
		);

		List<LinhaParaImportar> linhas = List.of(primeiraLinha, segundaLinha);

		Document document205 = new Document("");
		Document document002 = new Document("");

		LinhaRequestDto request205 =
				new LinhaRequestDto(
						"205",
						"Terminal Metrópole",
						TipoTarifa.METROPOLITANA
				);

		LinhaRequestDto request002 =
				new LinhaRequestDto(
						"002",
						"Linha Urbana",
						TipoTarifa.URBANA_ZERO
				);

		LinhaEntity entity205 = new LinhaEntity();
		LinhaEntity entity002 = new LinhaEntity();

		List<HorarioRequestDto> horario205 = List.of();
		List<HorarioRequestDto> horario002 = List.of();


		when(empresaVitoriaClient.buscarCatalogo()).thenReturn(catalogo);

		when(empresaVitoriaParser.extrairCatalogo(catalogo)).thenReturn(linhas);

		// Primeira Linha

		when(empresaVitoriaClient.buscarPagina("205_11-terminal-metropole"))
				.thenReturn(document205);

		when(empresaVitoriaParser.extrairLinha(document205, TipoTarifa.METROPOLITANA))
				.thenReturn(request205);

		when(linhaService.salvarOuBuscarExistente(request205)).thenReturn(entity205);

		when(empresaVitoriaParser.extrairHorarios(document205, "205"))
				.thenReturn(horario205);

		// Segunda Linha

		when(empresaVitoriaClient.buscarPagina("linha-002"))
				.thenReturn(document002);

		when(empresaVitoriaParser.extrairLinha(document002, TipoTarifa.URBANA_ZERO))
				.thenReturn(request002);

		when(linhaService.salvarOuBuscarExistente(request002)).thenReturn(entity002);

		when(empresaVitoriaParser.extrairHorarios(document002, "002"))
				.thenReturn(horario002);

		int resultado = importacaoService.importarCatalogo();

		assertEquals(2, resultado);

		verify(empresaVitoriaClient).buscarCatalogo();

		verify(empresaVitoriaParser).extrairCatalogo(catalogo);

		verify(horarioService).substituirHorarios(entity205, horario205);

		verify(horarioService).substituirHorarios(entity002, horario002);


	}
}
