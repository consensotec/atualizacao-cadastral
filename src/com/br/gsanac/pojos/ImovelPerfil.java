package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 04/09/2014
 */
public class ImovelPerfil extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String descricao;
	
	// Index de acesso
    private static final int IPER_ID_INDEX = 1;
    private static final int IPER_DSIMOVELPERFIL_INDEX = 2;
    
    // Constantes
    public static final long NORMAL = 5;
   
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class ImovelPerfilColuna implements BaseColumns{
    	public static final String ID = "IPER_ID";
    	public static final String DESCRICAO = "IPER_DSIMOVELPERFIL";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	ImovelPerfilColuna.ID,
    	ImovelPerfilColuna.DESCRICAO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
   
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class ImovelPerfilColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(30) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static ImovelPerfil converteLinhaArquivoEmObjeto(List<String> c) {
    	ImovelPerfil imovelPerfil = new ImovelPerfil();
    	imovelPerfil.setId(c.get(IPER_ID_INDEX));
    	imovelPerfil.setDescricao(c.get(IPER_DSIMOVELPERFIL_INDEX));
        return imovelPerfil;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(ImovelPerfilColuna.ID, getId());
		values.put(ImovelPerfilColuna.DESCRICAO, getDescricao());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<ImovelPerfil> carregarListaEntidade(Cursor cursor) {		
		ArrayList<ImovelPerfil> listaImovelPerfil = new ArrayList<ImovelPerfil>();
		
		if ( cursor.moveToFirst() ) {
		do{
			int _id = cursor.getColumnIndex(ImovelPerfilColuna.ID);
			int _descricao = cursor.getColumnIndex(ImovelPerfilColuna.DESCRICAO);
			
			ImovelPerfil imovelPerfil = new ImovelPerfil();

			imovelPerfil.setId(cursor.getInt(_id));
			imovelPerfil.setDescricao(cursor.getString(_descricao));
			
			listaImovelPerfil.add(imovelPerfil);
			
		} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaImovelPerfil;
	}
	
	// Método converte um cursor em  objeto
	public ImovelPerfil carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(ImovelPerfilColuna.ID);
		int _descricao = cursor.getColumnIndex(ImovelPerfilColuna.DESCRICAO);
		
		ImovelPerfil imovelPerfil = new ImovelPerfil();

		if ( cursor.moveToFirst() ) {
			
			imovelPerfil.setId(cursor.getInt(_id));
			imovelPerfil.setDescricao(cursor.getString(_descricao));
		}

		cursor.close();
		return imovelPerfil;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "IMOVEL_PERFIL";
	}
}
