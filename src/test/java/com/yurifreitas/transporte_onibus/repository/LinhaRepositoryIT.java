package com.yurifreitas.transporte_onibus.repository;


import com.yurifreitas.transporte_onibus.config.TestContainersConfig;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestContainersConfig.class)
public class LinhaRepositoryIT {

	@Autowired
	private LinhaRepository linhaRepository;

	@Test
	void deveSalvarEBuscarLinhaPorNumero() {
		LinhaEntity linha = new LinhaEntity();
		linha.setNumeroLinha("999");
		linha.setNomeLinha("Linha 1");
		linha.setTarifa(TipoTarifa.METROPOLITANA);

		linhaRepository.save(linha);

		Optional<LinhaEntity> resultado = linhaRepository.findByNumeroLinha("999");

		assertTrue(resultado.isPresent());
		assertEquals("999", resultado.get().getNumeroLinha());
		assertEquals("Linha 1", resultado.get().getNomeLinha());
		assertEquals(TipoTarifa.METROPOLITANA, resultado.get().getTarifa());
	}

	@Test
	void deveBuscarLinhasPorTarifa() {

		LinhaEntity metropolitana = new LinhaEntity();
		metropolitana.setNumeroLinha("999");
		metropolitana.setNomeLinha("Linha Metropolitana");
		metropolitana.setTarifa(TipoTarifa.METROPOLITANA);

		LinhaEntity urbana = new LinhaEntity();
		urbana.setNumeroLinha("998");
		urbana.setNomeLinha("Linha Urbana");
		urbana.setTarifa(TipoTarifa.URBANA_ZERO);

		linhaRepository.save(metropolitana);
		linhaRepository.save(urbana);

		Pageable pageable = PageRequest.of(0,10);

		Page<LinhaEntity> resultado = linhaRepository.findByTarifa(TipoTarifa.METROPOLITANA, pageable);

		assertEquals(1, resultado.getTotalElements());
		assertEquals("999", resultado.getContent().getFirst().getNumeroLinha());
		assertEquals(TipoTarifa.METROPOLITANA, resultado.getContent().getFirst().getTarifa());
	}
}