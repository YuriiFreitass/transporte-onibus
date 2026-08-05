package com.yurifreitas.transporte_onibus.entity;

import com.yurifreitas.transporte_onibus.enums.TipoDia;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Setter
public class HorarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalTime horario;

	@ManyToOne
	@JoinColumn(name = "linha_id")
	private LinhaEntity linha;

	@Column(nullable = false)
	private String localPartida;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoDia dia;
}
