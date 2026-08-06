package com.yurifreitas.transporte_onibus.handle;

import com.yurifreitas.transporte_onibus.dto.CampoErrorDto;
import com.yurifreitas.transporte_onibus.dto.ErrorResponseDto;
import com.yurifreitas.transporte_onibus.exception.DadosEmpresaVitoriaInvalidosException;
import com.yurifreitas.transporte_onibus.exception.LinhaNaoEncontradaException;
import com.yurifreitas.transporte_onibus.exception.PaginaEmpresaVitoriaInacessivelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e){
		List<CampoErrorDto> campos = e
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> new CampoErrorDto(
						fieldError.getField(),
						fieldError.getDefaultMessage()
				))
				.toList();

		ErrorResponseDto erro = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Erro de valiação",
				campos);
		return ResponseEntity.badRequest().body(erro);
	}

	@ExceptionHandler(LinhaNaoEncontradaException.class)
	public ResponseEntity<ErrorResponseDto> handleLinhaNaoEncontradaException(
			LinhaNaoEncontradaException e
	) {
		ErrorResponseDto erro = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),
				e.getMessage(), List.of());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@ExceptionHandler(PaginaEmpresaVitoriaInacessivelException.class)
	public ResponseEntity<ErrorResponseDto> handlePaginaEmpresaVitoriaInacessivelException(
			PaginaEmpresaVitoriaInacessivelException e
	) {
		ErrorResponseDto erro = new ErrorResponseDto(HttpStatus.BAD_GATEWAY.value(), e.getMessage(),
				List.of());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(DadosEmpresaVitoriaInvalidosException.class)
	public ResponseEntity<ErrorResponseDto> handleDadosEmpresaVitoriaInvalidos(
			DadosEmpresaVitoriaInvalidosException e
	) {
		ErrorResponseDto erro = new ErrorResponseDto(HttpStatus.UNPROCESSABLE_CONTENT.value(), e.getMessage(),
				List.of());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(erro);
	}


}
