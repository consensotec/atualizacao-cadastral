package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsável pelo objeto LigacaoEsgotoSituacao
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class LigacaoEsgotoSituacao extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     LEST_ID_INDEX        = 1;
    private static final int     LEST_DESCRICAO_INDEX        = 2;


    private String descricao;

    public static final String[] colunas              = new String[] {
    	LigacaoEsgotoSituacaos.ID,
    	LigacaoEsgotoSituacaos.DESCRICAO
    	};

    public static final class LigacaoEsgotoSituacaos implements BaseColumns {
        public static final String ID = "LEST_ID";
        public static final String DESCRICAO = "LEST_DSLIGACAOESGOTO";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "LIGACAO_ESGOTO_SITUACAO";
	}
    
    public final class LigacaoEsgotoSituacaoTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static LigacaoEsgotoSituacao inserirDoArquivo(List<String> c) {
    	
    	LigacaoEsgotoSituacao ligacao = new LigacaoEsgotoSituacao();

    	ligacao.setId(c.get(LEST_ID_INDEX));
    	ligacao.setDescricao(c.get(LEST_DESCRICAO_INDEX));
        
        return ligacao;
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
  
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(LigacaoEsgotoSituacaos.ID, getId());
		values.put(LigacaoEsgotoSituacaos.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<LigacaoEsgotoSituacao> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<LigacaoEsgotoSituacao> ligacoes = new ArrayList<LigacaoEsgotoSituacao>();
		cursor.moveToFirst();
		
		do{
			int codigo = cursor.getColumnIndex(LigacaoEsgotoSituacaos.ID);
			int descricao = cursor.getColumnIndex(LigacaoEsgotoSituacaos.DESCRICAO);
			
			LigacaoEsgotoSituacao ligacao = new LigacaoEsgotoSituacao();
			
			ligacao.setId(cursor.getInt(codigo));
			ligacao.setDescricao(cursor.getString(descricao));
			
			ligacoes.add(ligacao);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return ligacoes;
	}
	
	public LigacaoEsgotoSituacao carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(LigacaoEsgotoSituacaos.ID);
		int descricao = cursor.getColumnIndex(LigacaoEsgotoSituacaos.DESCRICAO);
		
		LigacaoEsgotoSituacao ligacao = new LigacaoEsgotoSituacao();

		if ( cursor.moveToFirst() ) {
			
			ligacao.setId(cursor.getInt(codigo));
			ligacao.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return ligacao;
	}
}