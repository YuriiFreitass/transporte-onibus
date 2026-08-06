package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.dto.HorarioResponseDto;
import com.yurifreitas.transporte_onibus.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/horarios")
public class HorarioController {

	private final HorarioService horarioService;

	@GetMapping("/linha/{numeroLinha}")
	@ResponseStatus(HttpStatus.OK)
	public List<HorarioResponseDto> findByNumeroLinha(@PathVariable String numeroLinha) {
		return horarioService.findByNumeroLinha(numeroLinha);
	}
}
