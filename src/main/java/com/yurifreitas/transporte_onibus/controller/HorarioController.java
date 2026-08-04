package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.dto.HorarioResponseDto;
import com.yurifreitas.transporte_onibus.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/horarios")
public class HorarioController {

	private final HorarioService horarioService;

	@GetMapping("/linha/{numeroLinha}")
	public List<HorarioResponseDto> findByNumeroLinha(@PathVariable String numeroLinha) {
		return horarioService.findByNumeroLinha(numeroLinha);
	}
}
