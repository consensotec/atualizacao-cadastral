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
public class LigacaoAguaSituacao extends EntidadeBasica {

    private static final long serialVersionUID = 4327285475536305741L;

    // Atributos
    private String descricao;
    
    // Index de acesso
    private static final int LAST_ID_INDEX = 1;
    private static final int LAST_DESCRICAO_INDEX = 2;

    // Constantes
   	public static final int POTENCIAL = 1;
 	public static final int FACTIVEL = 2;
 	public static final int LIGADO = 3;
 	public static final int EM_FISCALIZACAO = 4;
 	public static final int CORTADO = 5;
 	public static final int SUPRIMIDO = 6;
 	public static final int SUPR_PARC = 7;
 	public static final int SUPR_PARC_PEDIDO = 8;
 	public static final int EM_CANCELAMENTO = 9;

 	/*
     * SubClasse referente aos 
     * campos da tabela
     */
 	public static final class LigacaoAguaSituacaoColuna implements BaseColumns {
 		public static final String ID = "LAST_ID";
 		public static final String DESCRICAO = "LAST_DSLIGACAOAGUASITUACAO";
        
    }
 	
 	/*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[] {
    	LigacaoAguaSituacaoColuna.ID,
    	LigacaoAguaSituacaoColuna.DESCRICAO
   	};
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class LigacaoAguaSituacaoColunaTipo {
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
    public static LigacaoAguaSituacao converteLinhaArquivoEmObjeto(List<String> c) {
    	LigacaoAguaSituacao ligacao = new LigacaoAguaSituacao();
    	ligacao.setId(c.get(LAST_ID_INDEX));
    	ligacao.setDescricao(c.get(LAST_DESCRICAO_INDEX));
        return ligacao;
    }
  
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(LigacaoAguaSituacaoColuna.ID, getId());
		values.put(LigacaoAguaSituacaoColuna.DESCRICAO, getDescricao());
		return values;
	}
	
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<LigacaoAguaSituacao> carregarListaEntidade(Cursor cursor) {		
		ArrayList<LigacaoAguaSituacao> ligacoes = new ArrayList<LigacaoAguaSituacao>();
		
		cursor.moveToFirst();
		
		do{			
			int _id = cursor.getColumnIndex(LigacaoAguaSituacaoColuna.ID);
			int _descricao = cursor.getColumnIndex(LigacaoAguaSituacaoColuna.DESCRICAO);
			
			LigacaoAguaSituacao ligacao = new LigacaoAguaSituacao();
			
			ligacao.setId(cursor.getInt(_id));
			ligacao.setDescricao(cursor.getString(_descricao));
			
			ligacoes.add(ligacao);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return ligacoes;
	}
	
	// Método converte um cursor em  objeto
	public LigacaoAguaSituacao carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(LigacaoAguaSituacaoColuna.ID);
		int _descricao = cursor.getColumnIndex(LigacaoAguaSituacaoColuna.DESCRICAO);
		
		LigacaoAguaSituacao ligacao = new LigacaoAguaSituacao();

		if ( cursor.moveToFirst() ) {
			
			ligacao.setId(cursor.getInt(_id));
			ligacao.setDescricao(cursor.getString(_descricao));
		}

		cursor.close();
		return ligacao;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "LIGACAO_AGUA_SITUACAO";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDescricao();
	}
}