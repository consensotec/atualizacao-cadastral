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
public class LigacaoEsgotoSituacao extends EntidadeBasica {

    private static final long serialVersionUID = 4327285475536305741L;

    // Atributos
    private String descricao;
    
    // Index de acesso
    private static final int LEST_ID_INDEX = 1;
    private static final int LEST_DESCRICAO_INDEX = 2;

    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class LigacaoEsgotoSituacaoColuna implements BaseColumns {
    	public static final String ID = "LEST_ID";
    	public static final String DESCRICAO = "LEST_DSLIGACAOESGOTOSITUACAO";
        
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[] {
    	LigacaoEsgotoSituacaoColuna.ID,
    	LigacaoEsgotoSituacaoColuna.DESCRICAO
    	};

    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class LigacaoEsgotoSituacaoTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
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
    public static LigacaoEsgotoSituacao converteLinhaArquivoEmObjeto(List<String> c) {
    	LigacaoEsgotoSituacao ligacao = new LigacaoEsgotoSituacao();
    	ligacao.setId(c.get(LEST_ID_INDEX));
    	ligacao.setDescricao(c.get(LEST_DESCRICAO_INDEX));
        return ligacao;
    }
  
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(LigacaoEsgotoSituacaoColuna.ID, getId());
		values.put(LigacaoEsgotoSituacaoColuna.DESCRICAO, getDescricao());
		return values;
	}
	
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<LigacaoEsgotoSituacao> carregarListaEntidade(Cursor cursor) {		
		ArrayList<LigacaoEsgotoSituacao> ligacoes = new ArrayList<LigacaoEsgotoSituacao>();
		
		if(cursor.moveToFirst()){
			do{
				int _id = cursor.getColumnIndex(LigacaoEsgotoSituacaoColuna.ID);
				int _descricao = cursor.getColumnIndex(LigacaoEsgotoSituacaoColuna.DESCRICAO);
				
				LigacaoEsgotoSituacao ligacao = new LigacaoEsgotoSituacao();
				
				ligacao.setId(cursor.getInt(_id));
				ligacao.setDescricao(cursor.getString(_descricao));
				
				ligacoes.add(ligacao);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return ligacoes;
	}
	
	// Método converte um cursor em  objeto
	public LigacaoEsgotoSituacao carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(LigacaoEsgotoSituacaoColuna.ID);
		int _descricao = cursor.getColumnIndex(LigacaoEsgotoSituacaoColuna.DESCRICAO);
		
		LigacaoEsgotoSituacao ligacao = new LigacaoEsgotoSituacao();

		if ( cursor.moveToFirst() ) {
			
			ligacao.setId(cursor.getInt(_id));
			ligacao.setDescricao(cursor.getString(_descricao));
		}

		cursor.close();
		return ligacao;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "LIGACAO_ESGOTO_SITUACAO";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDescricao();
	}
}