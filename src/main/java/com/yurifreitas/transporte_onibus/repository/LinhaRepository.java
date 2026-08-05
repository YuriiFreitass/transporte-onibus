package com.yurifreitas.transporte_onibus.repository;

import com.yurifreitas.transporte_onibus.dto.LinhaResponseDto;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface LinhaRepository extends JpaRepository<LinhaEntity, Long> {

	Page<LinhaEntity>findByTarifa(TipoTarifa tarifa,Pageable pageable);

	boolean existsByNumeroLinha(String numeroLinha);

	Optional<LinhaEntity> findByNumeroLinha(String numeroLinha);
}