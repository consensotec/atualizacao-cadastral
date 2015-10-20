package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 12/09/2014
 */
public class LeituraAnormalidade  extends EntidadeBasica {

	private static final long serialVersionUID = -6766882238338096903L;
	
	// Atributos
	private String descricao;
	
	// Index de acesso
	private static final int INDEX_LTAN_ID = 1;
	private static final int INDEX_LTAN_DSLEITURAANORMALIDADE = 2;
	
	 /*
     * SubClasse referente aos 
     * campos da tabela
     */
	public static final class LeituraAnormalidadeColuna implements BaseColumns{
		public static final String ID = "LTAN_ID";
		public static final String DESCRICAO = "LTAN_DSLEITURAANORMALIDADE";
	}
	
	/*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
	public static final String[] colunas = new String[]{
		LeituraAnormalidadeColuna.ID,
		LeituraAnormalidadeColuna.DESCRICAO
	};
	
	// Método retorna as colunas da tabela
	public String[] getColunas(){
		return colunas; 
	}
	
	/*
    * SubClasse referente aos
    * tipos das colunas
    */
	public final class LeituraAnormalidadeColunaTipo{
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(25) NOT NULL";
		
		private String[] tipos = new String[]{ID,DESCRICAO};
		
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
	public static LeituraAnormalidade converteLinhaArquivoEmObjeto(List<String> c) {
	   	LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
	   	leituraAnormalidade.setId(Integer.valueOf(c.get(INDEX_LTAN_ID)));
	   	leituraAnormalidade.setDescricao(c.get(INDEX_LTAN_DSLEITURAANORMALIDADE));
	   	return leituraAnormalidade;
	}
	
	// Método retorna ContentValues
	public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(LeituraAnormalidadeColuna.ID, getId());
		values.put(LeituraAnormalidadeColuna.DESCRICAO, getDescricao());
		return values;
	}
	
	/*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<LeituraAnormalidade> carregarListaEntidade(Cursor cursor) {		
		ArrayList<LeituraAnormalidade> listaLeituraAnormalidade = new ArrayList<LeituraAnormalidade>();
		if ( cursor.moveToFirst() ) {
			do{
				LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
				int _id = cursor.getColumnIndex(LeituraAnormalidadeColuna.ID);
				int _descricao = cursor.getColumnIndex(LeituraAnormalidadeColuna.DESCRICAO);
				
				leituraAnormalidade.setId(cursor.getInt(_id));
				leituraAnormalidade.setDescricao(cursor.getString(_descricao));
				
				listaLeituraAnormalidade.add(leituraAnormalidade);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaLeituraAnormalidade;
	}
	
	// Método converte um cursor em  objeto
	public LeituraAnormalidade carregarEntidade(Cursor cursor) {
			
		int _id = cursor.getColumnIndex(LeituraAnormalidadeColuna.ID);
		int _descricao = cursor.getColumnIndex(LeituraAnormalidadeColuna.DESCRICAO);
		
		LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
		if (cursor.moveToFirst()) {
			leituraAnormalidade.setId(cursor.getInt(_id));
			leituraAnormalidade.setDescricao(cursor.getString(_descricao));
		}
		cursor.close();
		return leituraAnormalidade;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDescricao();
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "LEITURA_ANORMALIDADE";
	}
}
