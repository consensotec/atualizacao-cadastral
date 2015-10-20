package com.br.gsanac.excecoes;

import com.br.gsanac.utilitarios.Utilitarios;

public class ActivityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActivityException(String mensagem){
		super(mensagem);
		Utilitarios.gerarLogCat(mensagem);
	}
}