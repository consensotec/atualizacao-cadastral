package com.br.gsanac.excecoes;

import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class RepositorioException extends Exception {

	private static final long serialVersionUID = 1L;

	public RepositorioException(String mensagem){
		super(mensagem);
		Utilitarios.gerarLogCat(mensagem);
	}
}