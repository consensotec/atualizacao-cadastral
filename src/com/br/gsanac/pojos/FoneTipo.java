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
public class FoneTipo extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String descricao;
	
	// Index de acesso
    private static final int FNET_ID_INDEX = 1;
    private static final int FNET_DSFONETIPO_INDEX = 2;
    
    // Constantes
    public static final int RESIDENCIAL = 1;
	public static final int COMERCIAL = 2;
	public static final int CELULAR = 3;
	public static final int FAX = 4;
	
	/*
     * SubClasse referente aos 
     * campos da tabela
     */
	public static final class FoneTipoColuna implements BaseColumns{
		public static final String ID = "FNET_ID";
		public static final String DESCRICAO = "FNET_DSFONETIPO";
    }
	
	/*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	FoneTipoColuna.ID,
    	FoneTipoColuna.DESCRICAO
    };
   
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class FoneTipoColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
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
    public static FoneTipo converteLinhaArquivoEmObjeto(List<String> c) {
    	FoneTipo foneTipo = new FoneTipo();
    	foneTipo.setId(c.get(FNET_ID_INDEX));
    	foneTipo.setDescricao(c.get(FNET_DSFONETIPO_INDEX));
        return foneTipo;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(FoneTipoColuna.ID, getId());
		values.put(FoneTipoColuna.DESCRICAO, getDescricao());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<FoneTipo> carregarListaEntidade(Cursor cursor) {		
		ArrayList<FoneTipo> listaFoneTipo = new ArrayList<FoneTipo>();
		
		if ( cursor.moveToFirst() ) {
			do{
				FoneTipo foneTipo = new FoneTipo();
				int _id = cursor.getColumnIndex(FoneTipoColuna.ID);
				int _descricao = cursor.getColumnIndex(FoneTipoColuna.DESCRICAO);
				
				foneTipo.setId(cursor.getInt(_id));
				foneTipo.setDescricao(cursor.getString(_descricao));
				
				listaFoneTipo.add(foneTipo);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaFoneTipo;
	}
	
	// Método converte um cursor em  objeto
	public FoneTipo carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(FoneTipoColuna.ID);
		int _descricao = cursor.getColumnIndex(FoneTipoColuna.DESCRICAO);
		
		FoneTipo foneTipo = new FoneTipo();

		if ( cursor.moveToFirst() ) {
			
			foneTipo.setId(cursor.getInt(_id));
			foneTipo.setDescricao(cursor.getString(_descricao));
		}

		cursor.close();
		return foneTipo;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "FONE_TIPO";
	}

	@Override
	public String toString() {
		return descricao;
	}
}
