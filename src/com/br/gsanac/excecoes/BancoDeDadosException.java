package com.br.gsanac.excecoes;

import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class BancoDeDadosException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BancoDeDadosException(String mensagem){
		super(mensagem);
		Utilitarios.gerarLogCat(mensagem);
	}
}