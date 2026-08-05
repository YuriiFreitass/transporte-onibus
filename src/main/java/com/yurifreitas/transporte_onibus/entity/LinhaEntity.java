package com.yurifreitas.transporte_onibus.entity;

import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_linhas")
public class LinhaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String numeroLinha;

	@Column(nullable = false)
	private String nomeLinha;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoTarifa tarifa;

}
