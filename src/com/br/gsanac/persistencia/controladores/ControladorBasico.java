package com.br.gsanac.persistencia.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.gsanac.excecoes.ControladorException;
import com.br.gsanac.excecoes.RepositorioException;
import com.br.gsanac.persistencia.interfaces.InterfaceControladorBasico;
import com.br.gsanac.persistencia.repositorios.RepositorioBasico;
import com.br.gsanac.pojos.EntidadeBasica;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class ControladorBasico implements InterfaceControladorBasico {

	private static ControladorBasico controladorBasico;
	protected static Context contextControladorBasico;
	
	public static ControladorBasico getInstancia(){
		if(controladorBasico==null){
			controladorBasico = new ControladorBasico();
		}
		return controladorBasico;
	}
	
	public static void setContext(Context context){
		contextControladorBasico = context;
		RepositorioBasico.setContext(context);
	}
	
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
			throws ControladorException{
		try {
			return RepositorioBasico.getInstancia().inserirGenerico(objeto);
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}
	
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
			throws ControladorException{
		try {
			RepositorioBasico.getInstancia().atualizarGenerico(objeto);
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}
	
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
			throws ControladorException{
		try {
			RepositorioBasico.getInstancia().removerGenerico(objeto);
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}
	
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
			String atributosWhere,String[] valorAtributosWhere,String groupBy,String orderBy) throws ControladorException{
		try {
			return RepositorioBasico.getInstancia().pesquisarObjetoGenerico(classe, 
					atributosWhere, valorAtributosWhere, groupBy, orderBy);
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}
	
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
			String atributosWhere,String[] valorAtributosWhere,String groupBy,String orderBy) throws ControladorException{
		try {
			return RepositorioBasico.getInstancia().pesquisarListaObjetoGenerico(classe, 
					atributosWhere, valorAtributosWhere, groupBy, orderBy);
		} catch (RepositorioException e) {
			throw  new ControladorException(e.getMessage());
		}
	}
	
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
			throws ControladorException{
		try {
			return RepositorioBasico.getInstancia().pesquisarListaTodosObjetosGenerico(classe);
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}

	public void iniciarTransacao() throws ControladorException {
		try {
			RepositorioBasico.getInstancia().iniciarTransacao();
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}

	public void commitTransacao() throws ControladorException {
		try {
			RepositorioBasico.getInstancia().commitTransacao();
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}

	public void finalizarTransacao() throws ControladorException {
		try {
			RepositorioBasico.getInstancia().finalizarTransacao();
		} catch (RepositorioException e) {
			throw new ControladorException(e.getMessage());
		}
	}
}