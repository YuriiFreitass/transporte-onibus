package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.dto.LinhaResponseDto;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.service.LinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/linhas")
public class LinhaController {

	private final LinhaService linhaService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<LinhaResponseDto> findAll(Pageable pageable) {
		return linhaService.findAll(pageable);
	}

	@GetMapping("/tarifa/{tarifa}")
	@ResponseStatus(HttpStatus.OK)
	public Page<LinhaResponseDto> findByTarifa(@PathVariable TipoTarifa tarifa, Pageable pageable) {
		return linhaService.findByTarifa(tarifa, pageable);
	}
}
