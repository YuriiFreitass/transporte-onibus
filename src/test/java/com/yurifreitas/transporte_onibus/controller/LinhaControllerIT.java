package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.config.TestContainersConfig;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.repository.LinhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainersConfig.class)
public class LinhaControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LinhaRepository linhaRepository;

	@BeforeEach
	void setUp() {
		linhaRepository.deleteAll();


		LinhaEntity linha = new LinhaEntity();
		linha.setNumeroLinha("999");
		linha.setNomeLinha("Linha");
		linha.setTarifa(TipoTarifa.METROPOLITANA);

		linhaRepository.saveAndFlush(linha);
	}

	@Test
	void deveListarLinhas() throws Exception {
		mockMvc.perform(get("/v1/linhas"))
				.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content[0].numeroLinha").value("999"))
		.andExpect(jsonPath("$.content[0].nomeLinha").value("Linha"))
		.andExpect(jsonPath("$.content[0].tarifa").value("METROPOLITANA"));
	}

	@Test
	void deveListarLinhasPorTarifa() throws Exception {
		LinhaEntity urbana = new LinhaEntity();
		urbana.setNumeroLinha("998");
		urbana.setNomeLinha("Linha Urbana");
		urbana.setTarifa(TipoTarifa.URBANA_ZERO);

		linhaRepository.saveAndFlush(urbana);

		mockMvc.perform(get("/v1/linhas/tarifa/URBANA_ZERO"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("$.content[0].numeroLinha").value("998"))
				.andExpect(jsonPath("$.content[0].nomeLinha").value("Linha Urbana"))
				.andExpect(jsonPath("$.content[0].tarifa").value("URBANA_ZERO"));
	}
}
