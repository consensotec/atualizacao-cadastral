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
public class FonteAbastecimento extends EntidadeBasica {

    private static final long    serialVersionUID     = 4327285475536305741L;
    
    // Atributos
    private String descricao;
    
    // Index de acesso
    private static final int FTAB_ID_INDEX = 1;
    private static final int FTAB_DESCRICAO_INDEX = 2;
    
    // Constantes
 	public static final int CAERN = 1;
 	public static final int POCO = 2;
 	public static final int VIZINHO = 3;
 	public static final int CACIMBA = 4;
 	public static final int CHAFARIZ = 5;
 	public static final int CARRO_PIPA = 6;
 	public static final int SEM_ABASTECIMENTO = 7;
 	
 	/*
     * SubClasse referente aos 
     * campos da tabela
     */
 	public static final class FonteAbastecimentoColuna implements BaseColumns {
 		public static final String ID = "FTAB_ID";
 		public static final String DESCRICAO = "FTAB_DSFONTEABASTECIMENTO";
    }
 	
 	/*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[] {
    	FonteAbastecimentoColuna.ID,
    	FonteAbastecimentoColuna.DESCRICAO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class FonteAbastecimentoColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(25) NOT NULL";
		
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
    public static FonteAbastecimento converteLinhaArquivoEmObjeto(List<String> c) {
    	FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
    	fonteAbastecimento.setId(c.get(FTAB_ID_INDEX));
    	fonteAbastecimento.setDescricao(c.get(FTAB_DESCRICAO_INDEX));
        return fonteAbastecimento;
    }
  
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(FonteAbastecimentoColuna.ID, getId());
		values.put(FonteAbastecimentoColuna.DESCRICAO, getDescricao());
		return values;
	}
	
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<FonteAbastecimento> carregarListaEntidade(Cursor cursor) {		
		ArrayList<FonteAbastecimento> fonteAbastecimentos = new ArrayList<FonteAbastecimento>();
		if(cursor.moveToFirst()){
			do{
				FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
				
				int _id = cursor.getColumnIndex(FonteAbastecimentoColuna.ID);
				int _descricao = cursor.getColumnIndex(FonteAbastecimentoColuna.DESCRICAO);
				
				fonteAbastecimento.setId(cursor.getInt(_id));
				fonteAbastecimento.setDescricao(cursor.getString(_descricao));
				
				fonteAbastecimentos.add(fonteAbastecimento);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return fonteAbastecimentos;
	}
	
	// Método converte um cursor em  objeto
	public FonteAbastecimento carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(FonteAbastecimentoColuna.ID);
		int _descricao = cursor.getColumnIndex(FonteAbastecimentoColuna.DESCRICAO);
		
		FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();

		if ( cursor.moveToFirst() ) {
			
			fonteAbastecimento.setId(cursor.getInt(_id));
			fonteAbastecimento.setDescricao(cursor.getString(_descricao));
		}

		cursor.close();
		return fonteAbastecimento;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "FONTE_ABASTECIMENTO";
	}

	@Override
	public String toString() {
		return descricao;
	}
}