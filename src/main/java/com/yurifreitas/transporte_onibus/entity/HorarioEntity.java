package com.yurifreitas.transporte_onibus.entity;

import com.yurifreitas.transporte_onibus.enums.TipoDia;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class HorarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalTime horario;

	@ManyToOne
	@JoinColumn(name = "linha_id")
	private LinhaEntity linha;

	@Enumerated(EnumType.STRING)
	private TipoDia dia;
}
