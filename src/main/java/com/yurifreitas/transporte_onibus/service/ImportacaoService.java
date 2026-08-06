package com.yurifreitas.transporte_onibus.service;

import com.yurifreitas.transporte_onibus.client.EmpresaVitoriaClient;
import com.yurifreitas.transporte_onibus.client.EmpresaVitoriaParser;
import com.yurifreitas.transporte_onibus.client.LinhaParaImportar;
import com.yurifreitas.transporte_onibus.dto.HorarioRequestDto;
import com.yurifreitas.transporte_onibus.dto.LinhaRequestDto;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.exception.DadosEmpresaVitoriaInvalidosException;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportacaoService {

	private final EmpresaVitoriaClient empresaVitoriaClient;
	private final EmpresaVitoriaParser empresaVitoriaParser;
	private final LinhaService linhaService;
	private final HorarioService horarioService;

	@Transactional
	public void importar(
			String identificadorPagina,
			TipoTarifa tarifa
	) {
		Document document =
				empresaVitoriaClient.buscarPagina(identificadorPagina);

		LinhaRequestDto linhaRequest =
				empresaVitoriaParser.extrairLinha(document, tarifa);

		LinhaEntity linha =
				linhaService.salvarOuBuscarExistente(linhaRequest);

		List<HorarioRequestDto> horarios =
				empresaVitoriaParser.extrairHorarios(
						document,
						linhaRequest.numeroLinha()
				);

		horarioService.substituirHorarios(
				linha,
				horarios
		);
	}
	public int importarCatalogo() {
		Document catalogo =
				empresaVitoriaClient.buscarCatalogo();

		List<LinhaParaImportar> linhas =
				empresaVitoriaParser.extrairCatalogo(catalogo);

		if (linhas.isEmpty()) {
			throw new DadosEmpresaVitoriaInvalidosException(
					"Nenhuma linha foi encontrada no catálogo da Empresa Vitória");
		}

		for (LinhaParaImportar linha : linhas) {
			importar(
					linha.identificadorPagina(),
					linha.tarifa()
			);
		}

		return linhas.size();
	}
}