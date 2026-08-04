package com.yurifreitas.transporte_onibus.repository;

import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinhaRepository extends JpaRepository<LinhaEntity, Long> {
}
