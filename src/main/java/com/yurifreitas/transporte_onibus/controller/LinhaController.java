package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.dto.LinhaResponseDto;
import com.yurifreitas.transporte_onibus.enums.TipoTarifa;
import com.yurifreitas.transporte_onibus.service.LinhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(
		name = "Linhas",
		description = "Endpoints para consulta das linhas de transporte público"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/linhas")
public class LinhaController {

	private final LinhaService linhaService;

	@Operation(summary = "Listar linhas", description = "Retorna as linhas de transporte de forma paginada")
	@ApiResponse(responseCode = "200", description = "Linhas retornadas com sucesso")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<LinhaResponseDto> findAll(Pageable pageable) {
		return linhaService.findAll(pageable);
	}

	@Operation(summary = "Listar linhas por tarifa", description = "Retorna as linhas filtradas pelo tipo de tarifa")
	@ApiResponse(responseCode = "200", description = "Linhas retornadas com sucesso")
	@GetMapping("/tarifa/{tarifa}")
	@ResponseStatus(HttpStatus.OK)
	public Page<LinhaResponseDto> findByTarifa(
			@PathVariable TipoTarifa tarifa,
			Pageable pageable
	) {
		return linhaService.findByTarifa(tarifa, pageable);
	}
}
