package com.yurifreitas.transporte_onibus.repository;

import com.yurifreitas.transporte_onibus.entity.OnibusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnibusRepository extends JpaRepository<OnibusEntity, Long> {
}
