package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.config.TestContainersConfig;
import com.yurifreitas.transporte_onibus.entity.HorarioEntity;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoDia;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.repository.HorarioRepository;
import com.yurifreitas.transporte_onibus.repository.LinhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig.class)
class HorarioControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LinhaRepository linhaRepository;

	@Autowired
	private HorarioRepository horarioRepository;

	@BeforeEach
	void setUp() {
		horarioRepository.deleteAll();
		linhaRepository.deleteAll();

		LinhaEntity linha = new LinhaEntity();
		linha.setNumeroLinha("101");
		linha.setNomeLinha("Linha Teste");
		linha.setTarifa(TipoTarifa.METROPOLITANA);

		LinhaEntity linhaSalva = linhaRepository.saveAndFlush(linha);

		HorarioEntity horario = new HorarioEntity();
		horario.setHorario(LocalTime.of(5, 30));
		horario.setLinha(linhaSalva);
		horario.setLocalPartida("Metrópole");
		horario.setDia(TipoDia.DIAS_UTEIS);

		horarioRepository.saveAndFlush(horario);
	}

	@Test
	void deveListarHorariosPorNumeroDaLinha() throws Exception {
		mockMvc.perform(get("/v1/horarios/linha/101"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].numeroLinha").value("101"))
				.andExpect(jsonPath("$[0].horario").value("05:30:00"))
				.andExpect(jsonPath("$[0].dia").value("DIAS_UTEIS"))
				.andExpect(jsonPath("$[0].localPartida").value("Metrópole"));
	}

	@Test
	void deveRetornarNotFoundQuandoLinhaNaoExistir() throws Exception {
		mockMvc.perform(get("/v1/horarios/linha/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.mensagem").value("Linha não encontrada: 999"))
				.andExpect(jsonPath("$.campos").isArray())
				.andExpect(jsonPath("$.campos").isEmpty());
	}


}