package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto UnidadeFederacao
 * </p>
 * 
 * @author Davi Menezes
 * @since  27/12/2012
 */
public class UnidadeFederacao extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int UNFE_ID_INDEX        = 1;
    private static final int UNFE_DSUFSIGLA_INDEX = 2;

    private String descricao;
    
    public static final String[] columns = new String[]{
    	UnidadeFederacaoColunas.ID,
    	UnidadeFederacaoColunas.DESCRICAO
    };
    
    public static final class UnidadeFederacaoColunas implements BaseColumns{
    	public static final String ID 		 = "UNFE_ID";
    	public static final String DESCRICAO = "UNFE_DSUFSIGLA";
    }
	
    public final class UnidadeFederacaoColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(2) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static UnidadeFederacao inserirDoArquivo(List<String> c) {
    	
    	UnidadeFederacao unidadeFederacao = new UnidadeFederacao();

    	unidadeFederacao.setId(c.get(UNFE_ID_INDEX));
    	unidadeFederacao.setDescricao(c.get(UNFE_DSUFSIGLA_INDEX));
        
        return unidadeFederacao;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(UnidadeFederacaoColunas.ID , getId());
		values.put(UnidadeFederacaoColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<UnidadeFederacao> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<UnidadeFederacao> listaUnidadeFederacao = new ArrayList<UnidadeFederacao>();
		if ( cursor.moveToFirst() ) {
			do{
				int codigo 	  = cursor.getColumnIndex(UnidadeFederacaoColunas.ID);
				int descricao = cursor.getColumnIndex(UnidadeFederacaoColunas.DESCRICAO);
				
				UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
	
				unidadeFederacao.setId(cursor.getInt(codigo));
				unidadeFederacao.setDescricao(cursor.getString(descricao));
				
				listaUnidadeFederacao.add(unidadeFederacao);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaUnidadeFederacao;
	}
	
	public UnidadeFederacao carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(UnidadeFederacaoColunas.ID);
		int descricao = cursor.getColumnIndex(UnidadeFederacaoColunas.DESCRICAO);
		
		UnidadeFederacao unidadeFederacao = new UnidadeFederacao();

		if ( cursor.moveToFirst() ) {
			
			unidadeFederacao.setId(cursor.getInt(codigo));
			unidadeFederacao.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return unidadeFederacao;
	}
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "UNIDADE_FEDERACAO";
	}
}
