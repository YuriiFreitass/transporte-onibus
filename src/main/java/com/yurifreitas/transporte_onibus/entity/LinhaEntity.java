package com.yurifreitas.transporte_onibus.entity;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_onibus")
public class LinhaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomeLinha;

	@Enumerated(EnumType.STRING)
	private TipoTarifa tarifa;
}
