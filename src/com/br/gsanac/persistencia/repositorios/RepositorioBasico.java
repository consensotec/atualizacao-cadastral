package com.br.gsanac.persistencia.repositorios;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.br.api.core.Retorno;
import com.br.gsanac.excecoes.RepositorioException;
import com.br.gsanac.persistencia.conexao.BancoDeDadosHelper;
import com.br.gsanac.persistencia.interfaces.InterfaceRepositorioBasico;
import com.br.gsanac.pojos.EntidadeBasica;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class RepositorioBasico implements InterfaceRepositorioBasico {

	private static RepositorioBasico repositorioBasico;
	protected static Context contextRepositorioBasico;
	
	private RepositorioBasico(){}
	
	public static RepositorioBasico getInstancia(){
		if(repositorioBasico==null){
			repositorioBasico = new RepositorioBasico();
		}
		return repositorioBasico;
	}
	
	public static void setContext(Context context){
		contextRepositorioBasico = context;
	}
	
	public static void removerInstancia(){
		repositorioBasico = null;
	}
	
	/**
	 * @author Jonathan Marcos
	 * @since 05/09/2014 
	 * @return SQLiteDatabase
	 */
	/*
	 * Método responsável por
	 * obter pool de conexão
	 * com banco de dados
	 */
	private SQLiteDatabase obterConexaoBancoDeDados(){
		return 	BancoDeDadosHelper.getInstancia(contextRepositorioBasico).
				obterConexaoBancoDeDados();
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
			throws RepositorioException{
		long idRetorno = 0;
		try {
			idRetorno = obterConexaoBancoDeDados().insert(Retorno.retornarNomeDaTabelaObjeto(objeto),
					null, (ContentValues)Retorno.retornarContentValuesObjeto(objeto));
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
		}
		return idRetorno;
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
			throws RepositorioException{
		try {
			obterConexaoBancoDeDados().update(Retorno.retornarNomeDaTabelaObjeto(objeto),
				(ContentValues)Retorno.retornarContentValuesObjeto(objeto),
					Retorno.retornaWhereNomeCampoIdObjeto(objeto), 
							Retorno.retornaWhereValorCampoIdObjeto(objeto));
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
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
			throws RepositorioException{
		try {
			obterConexaoBancoDeDados().delete(Retorno.retornarNomeDaTabelaObjeto(objeto),
					Retorno.retornaWhereNomeCampoIdObjeto(objeto), 
						Retorno.retornaWhereValorCampoIdObjeto(objeto));
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
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
			String atributosWhere,String[] valorAtributosWhere,String groupBy,String orderBy) throws RepositorioException{
		try {
			Cursor cursor = obterConexaoBancoDeDados().query(Retorno.retornarNomeTabelaObjeto(classe), Retorno.retornaGetColunasObjeto(classe), 
					atributosWhere, valorAtributosWhere, groupBy, null, orderBy);
			
			if(cursor.getCount()!=0){
				return Retorno.retornaCarregarEntidadeObjeto(classe,cursor);
			}
			return null;
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
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
			String atributosWhere,String[] valorAtributosWhere,String groupBy,String orderBy) throws RepositorioException{
		try {
			Cursor cursor = obterConexaoBancoDeDados().query(Retorno.retornarNomeTabelaObjeto(classe),
					Retorno.retornaGetColunasObjeto(classe), atributosWhere, valorAtributosWhere, groupBy, null, orderBy);
			
			if(cursor.getCount()!=0){
				return Retorno.retornarCarregarListaEntidadeObjeto(classe, cursor);
			}
			return null;
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
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
			throws RepositorioException{
		try {
			Cursor cursor = obterConexaoBancoDeDados().query(Retorno.retornarNomeTabelaObjeto(classe), 
					Retorno.retornaGetColunasObjeto(classe), null, null, null, null, null);
			if(cursor.getCount()!=0){
				return Retorno.retornarCarregarListaEntidadeObjeto(classe, cursor);
			}
			return null;
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
		}
	}

	public void iniciarTransacao() throws RepositorioException {
		try {
			obterConexaoBancoDeDados().beginTransaction();
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
		}
	}

	public void commitTransacao() throws RepositorioException {
		try {
			obterConexaoBancoDeDados().setTransactionSuccessful();
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
		}
	}

	public void finalizarTransacao() throws RepositorioException {
		try {
			obterConexaoBancoDeDados().endTransaction();
		} catch (Exception e) {
			throw new RepositorioException(e.getMessage());
		}
	}
}