package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsável pelo objeto EnderecoReferencia
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class EnderecoReferencia extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     EDRF_ID_INDEX        = 1;
    private static final int     EDRF_DESCRICAO_INDEX        = 2;
    private static final int     EDRF_DESCRICAO_ABREVIADA_INDEX        = 3;


    private String descricao;
    
    private String descricaoAbreviada;

    public static final String[] colunas              = new String[] {
    	EnderecoReferencias.ID,
    	EnderecoReferencias.DESCRICAO,
    	EnderecoReferencias.DESCRICAO_ABREVIADA
    	};

    public static final class EnderecoReferencias implements BaseColumns {
        public static final String ID = "EDRF_ID";
        public static final String DESCRICAO = "EDRF_DSENDERECOREFERENCIA";
        public static final String DESCRICAO_ABREVIADA = "EDRF_DSABREVIADO";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "ENDERECO_REFERENCIA";
	}
    
    public final class EnderecoReferenciaTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) ";
		public final String DESCRICAO_ABREVIADA = " VARCHAR(18) ";
		
		private String[] tipos = new String[] {ID, DESCRICAO, DESCRICAO_ABREVIADA};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static EnderecoReferencia inserirDoArquivo(List<String> c) {
    	
    	EnderecoReferencia enderecoReferencia = new EnderecoReferencia();

    	enderecoReferencia.setId(c.get(EDRF_ID_INDEX));
    	enderecoReferencia.setDescricao(c.get(EDRF_DESCRICAO_INDEX));
        enderecoReferencia.setDescricaoAbreviada(c.get(EDRF_DESCRICAO_ABREVIADA_INDEX));
    	
        return enderecoReferencia;
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

	public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(EnderecoReferencias.ID, getId());
		values.put(EnderecoReferencias.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<EnderecoReferencia> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<EnderecoReferencia> enderecoReferencias = new ArrayList<EnderecoReferencia>();
		
		do{
			EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
			
			int codigo = cursor.getColumnIndex(EnderecoReferencias.ID);
			int descricao = cursor.getColumnIndex(EnderecoReferencias.DESCRICAO);
			int descricaoAbreviada = cursor.getColumnIndex(EnderecoReferencias.DESCRICAO_ABREVIADA);
			
			enderecoReferencia.setId(cursor.getInt(codigo));
			enderecoReferencia.setDescricao(cursor.getString(descricao));
			enderecoReferencia.setDescricaoAbreviada(cursor.getString(descricaoAbreviada));
			
			enderecoReferencias.add(enderecoReferencia);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return enderecoReferencias;
	}
	
	public EnderecoReferencia carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(EnderecoReferencias.ID);
		int descricao = cursor.getColumnIndex(EnderecoReferencias.DESCRICAO);
		int descricaoAbreviada = cursor.getColumnIndex(EnderecoReferencias.DESCRICAO_ABREVIADA);
		
		EnderecoReferencia enderecoReferencia = new EnderecoReferencia();

		if ( cursor.moveToFirst() ) {
			
			enderecoReferencia.setId(cursor.getInt(codigo));
			enderecoReferencia.setDescricao(cursor.getString(descricao));
			enderecoReferencia.setDescricaoAbreviada(cursor.getString(descricaoAbreviada));
		}
		
		cursor.close();
		return enderecoReferencia;
	}
}