package com.yurifreitas.transporte_onibus.exception;
public class DadosEmpresaVitoriaInvalidosException
		extends RuntimeException {

	public DadosEmpresaVitoriaInvalidosException(String mensagem) {
		super(mensagem);
	}

	public DadosEmpresaVitoriaInvalidosException(
			String mensagem,
			Throwable causa
	) {
		super(mensagem, causa);
	}
}