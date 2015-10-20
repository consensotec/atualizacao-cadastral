package com.br.gsanac.persistencia.interfaces;

import java.util.ArrayList;

import com.br.gsanac.excecoes.RepositorioException;
import com.br.gsanac.pojos.EntidadeBasica;

public interface InterfaceRepositorioBasico {

	/**
	 * @author Jonathan Marcos
	 * @since 05/09/2014
	 * @param objeto
	 * @throws RepositorioException
	 */
	/*
	 * Método responsável por
	 * inserir objeto genêrico
	 */
	public <T extends EntidadeBasica> long inserirGenerico(T objeto) 
			throws RepositorioException;
	
	/**
	 * @author Jonathan Marcos
	 * @since 05/09/2014
	 * @param objeto
	 * @throws RepositorioException
	 */
	/*
	 * Método responsável por
	 * atualizar um objeto
	 * genêrico pelo id
	 */
	public <T extends EntidadeBasica> void atualizarGenerico(T objeto) 
			throws RepositorioException;
	
	/**
	 * @author Jonathan Marcos
	 * @since 05/09/2014
	 * @param objeto
	 * @throws RepositorioException
	 */
	/*
	 * Método responsável por 
	 * remover um objeto 
	 * genêrico pelo id
	 */
	public <T extends EntidadeBasica>  void removerGenerico(T objeto) 
			throws RepositorioException;
	
	/**
	 * @author Jonathan Marcos
	 * @since 08/09/2014
	 * @param classe
	 * @param atributosWhere
	 * @param valorAtributosWhere
	 * @return <T> T
	 */
	/*
	 * Método responsável por 
	 * pesquisar objeto
	 * genêrico
	 */
	public <T> T pesquisarObjetoGenerico (Class<? extends EntidadeBasica> classe,
			String atributosWhere,String[] valorAtributosWhere,String groupBy,String orderBy) throws RepositorioException;
	
	/**
	 * @author Jonathan Marcos
	 * @since 08/09/2014
	 * @param classe
	 * @param atributosWhere
	 * @param valorAtributosWhere
	 * @return <T> ArrayList<T>
	 */
	/*
	 * Método responsável por 
	 * pesquisar lista de 
	 * objeto genêrico
	 */
	public <T> ArrayList<T> pesquisarListaObjetoGenerico(Class<? extends EntidadeBasica> classe,
			String atributosWhere,String[] valorAtributosWhere,String groupBy,String orderBy) throws RepositorioException;
	
	/**
	 * @author Jonathan Marcos
	 * @since 08/09/2014
	 * @param classe
	 * @return <T> ArrayList<T>
	 */
	/*
	 * Método responsável por 
	 * pesquisar lista de 
	 * objeto genêrico
	 * e retorna todos
	 * os objetos
	 */
	public <T> ArrayList<T> pesquisarListaTodosObjetosGenerico(Class<? extends EntidadeBasica> classe) 
			throws RepositorioException;
	
}
