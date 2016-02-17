package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsável pelo objeto LigacaoAguaSituacao
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class LigacaoAguaSituacao extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     LAST_ID_INDEX        = 1;
    private static final int     LAST_DESCRICAO_INDEX        = 2;

    // --CONSTANTES

 	public static final int POTENCIAL = 1;

 	public static final int FACTIVEL = 2;

 	public static final int LIGADO = 3;

 	public static final int EM_FISCALIZACAO = 4;

 	public static final int CORTADO = 5;

 	public static final int SUPRIMIDO = 6;

 	public static final int SUPR_PARC = 7;

 	public static final int SUPR_PARC_PEDIDO = 8;

 	public static final int EM_CANCELAMENTO = 9;

    private String descricao;

    public static final String[] colunas              = new String[] {
    	LigacaoAguaSituacaos.ID,
    	LigacaoAguaSituacaos.DESCRICAO
    	};

    public static final class LigacaoAguaSituacaos implements BaseColumns {
        public static final String ID = "LAST_ID";
        public static final String DESCRICAO = "LAST_DSLIGACAOAGUA";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "LIGACAO_AGUA_SITUACAO";
	}
    
    public final class LigacaoAguaSituacaoTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static LigacaoAguaSituacao inserirDoArquivo(List<String> c) {
    	
    	LigacaoAguaSituacao ligacao = new LigacaoAguaSituacao();

    	ligacao.setId(c.get(LAST_ID_INDEX));
    	ligacao.setDescricao(c.get(LAST_DESCRICAO_INDEX));
        
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
		
		values.put(LigacaoAguaSituacaos.ID, getId());
		values.put(LigacaoAguaSituacaos.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<LigacaoAguaSituacao> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<LigacaoAguaSituacao> ligacoes = new ArrayList<LigacaoAguaSituacao>();
		cursor.moveToFirst();
		
		do{			
			int codigo = cursor.getColumnIndex(LigacaoAguaSituacaos.ID);
			int descricao = cursor.getColumnIndex(LigacaoAguaSituacaos.DESCRICAO);
			
			LigacaoAguaSituacao ligacao = new LigacaoAguaSituacao();
			
			ligacao.setId(cursor.getInt(codigo));
			ligacao.setDescricao(cursor.getString(descricao));
			
			ligacoes.add(ligacao);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return ligacoes;
	}
	
	public LigacaoAguaSituacao carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(LigacaoAguaSituacaos.ID);
		int descricao = cursor.getColumnIndex(LigacaoAguaSituacaos.DESCRICAO);
		
		LigacaoAguaSituacao ligacao = new LigacaoAguaSituacao();

		if ( cursor.moveToFirst() ) {
			
			ligacao.setId(cursor.getInt(codigo));
			ligacao.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return ligacao;
	}
}