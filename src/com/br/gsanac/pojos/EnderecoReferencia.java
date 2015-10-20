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
public class EnderecoReferencia extends EntidadeBasica {
    
	private static final long    serialVersionUID     = 4327285475536305741L;
	
	// Atributos
	private String descricao;
    private String descricaoAbreviada;
    
    // Index de acesso
    private static final int EDRF_ID_INDEX = 1;
    private static final int EDRF_DESCRICAO_INDEX = 2;
    private static final int EDRF_DESCRICAO_ABREVIADA_INDEX = 3;

    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class EnderecoReferenciaColuna implements BaseColumns {
    	public static final String ID = "EDRF_ID";
    	public static final String DESCRICAO = "EDRF_DSENDERECOREFERENCIA";
    	public static final String DESCRICAO_ABREVIADA = "EDRF_DSABREVIADO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[] {
    	EnderecoReferenciaColuna.ID,
    	EnderecoReferenciaColuna.DESCRICAO,
    	EnderecoReferenciaColuna.DESCRICAO_ABREVIADA
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class EnderecoReferenciaColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(20) ";
		private final String DESCRICAO_ABREVIADA = " VARCHAR(18) ";
		
		private String[] tipos = new String[] {ID, DESCRICAO, DESCRICAO_ABREVIADA};	
		
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
    
  
    public String getDescricaoAbreviada() {
		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada) {
		this.descricaoAbreviada = descricaoAbreviada;
	}
    
	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static EnderecoReferencia converteLinhaArquivoEmObjeto(List<String> c) {
    	EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
    	enderecoReferencia.setId(c.get(EDRF_ID_INDEX));
    	enderecoReferencia.setDescricao(c.get(EDRF_DESCRICAO_INDEX));
        enderecoReferencia.setDescricaoAbreviada(c.get(EDRF_DESCRICAO_ABREVIADA_INDEX));
        return enderecoReferencia;
    }

    // Método retorna ContentValues
	public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(EnderecoReferenciaColuna.ID, getId());
		values.put(EnderecoReferenciaColuna.DESCRICAO, getDescricao());
		values.put(EnderecoReferenciaColuna.DESCRICAO_ABREVIADA, getDescricaoAbreviada());
		return values;
	}
	
	/*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<EnderecoReferencia> carregarListaEntidade(Cursor cursor) {		
		ArrayList<EnderecoReferencia> enderecoReferencias = new ArrayList<EnderecoReferencia>();
		if(cursor.moveToFirst()){
			do{
				EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
				
				int _id = cursor.getColumnIndex(EnderecoReferenciaColuna.ID);
				int _descricao = cursor.getColumnIndex(EnderecoReferenciaColuna.DESCRICAO);
				int _descricaoAbreviada = cursor.getColumnIndex(EnderecoReferenciaColuna.DESCRICAO_ABREVIADA);
				
				enderecoReferencia.setId(cursor.getInt(_id));
				enderecoReferencia.setDescricao(cursor.getString(_descricao));
				enderecoReferencia.setDescricaoAbreviada(cursor.getString(_descricaoAbreviada));
				
				enderecoReferencias.add(enderecoReferencia);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return enderecoReferencias;
	}
	
	// Método converte um cursor em  objeto
	public EnderecoReferencia carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(EnderecoReferenciaColuna.ID);
		int _descricao = cursor.getColumnIndex(EnderecoReferenciaColuna.DESCRICAO);
		int _descricaoAbreviada = cursor.getColumnIndex(EnderecoReferenciaColuna.DESCRICAO_ABREVIADA);
		
		EnderecoReferencia enderecoReferencia = new EnderecoReferencia();

		if ( cursor.moveToFirst() ) {
			
			enderecoReferencia.setId(cursor.getInt(_id));
			enderecoReferencia.setDescricao(cursor.getString(_descricao));
			enderecoReferencia.setDescricaoAbreviada(cursor.getString(_descricaoAbreviada));
		}
		
		cursor.close();
		return enderecoReferencia;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "ENDERECO_REFERENCIA";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDescricao();
	}
}