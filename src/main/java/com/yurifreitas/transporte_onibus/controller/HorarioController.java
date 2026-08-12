package com.yurifreitas.transporte_onibus.controller;

import com.yurifreitas.transporte_onibus.dto.HorarioResponseDto;
import com.yurifreitas.transporte_onibus.service.HorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Horários", description = "Endpoints para consulta dos horários das linhas de transporte público")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/horarios")
public class HorarioController {

	private final HorarioService horarioService;

	@Operation(summary = "Buscar horários por linha", description = "Retorna os horários disponíveis para uma linha a partir do número da linha")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Horários retornados com sucesso"),
			@ApiResponse(responseCode = "404", description = "Linha não encontrada")
	})
	@GetMapping("/linha/{numeroLinha}")
	@ResponseStatus(HttpStatus.OK)
	public List<HorarioResponseDto> findByNumeroLinha(
			@PathVariable String numeroLinha
	) {
		return horarioService.findByNumeroLinha(numeroLinha);
	}
}