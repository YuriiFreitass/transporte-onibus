package com.yurifreitas.transporte_onibus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_onibus")
public class OnibusEntity {

	private Long id;

	private String nomeLinha;

	private LocalDateTime horarioLinha;

}
