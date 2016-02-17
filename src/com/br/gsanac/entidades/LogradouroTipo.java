package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto LogradouroTipo
 * </p>
 * 
 * @author Anderson Cabral
 * @since  12/12/2012
 */
public class LogradouroTipo extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int LGTP_ID_INDEX         		 = 1;
    private static final int LGTP_DSLOGRADOUROTIPO_INDEX = 2;
    private static final int LGTP_DSABREVIADO_INDEX 	 = 3;

    private String descricao;
    private String descricaoAbreviada;
    
    public static final String[] columns = new String[]{
    	LogradouroTipoColunas.ID,
    	LogradouroTipoColunas.DESCRICAO,
    	LogradouroTipoColunas.DESCRICAOABREV
    };
    
    public static final class LogradouroTipoColunas implements BaseColumns{
    	public static final String ID 		 	  = "LGTP_ID";
    	public static final String DESCRICAO 	  = "LGTP_DSLOGRADOUROTIPO";
    	public static final String DESCRICAOABREV = "LGTP_DSABREVIADO";
    }
	
    public final class LogradouroTipoColunasTipo {
		public final String ID 		  	   = " INTEGER PRIMARY KEY";
		public final String DESCRICAO 	   = " VARCHAR(20) NOT NULL";
		public final String DESCRICAOABREV = " VARCHAR(3)";
		
		private String[] tipos = new String[] {ID, DESCRICAO, DESCRICAOABREV};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static LogradouroTipo inserirDoArquivo(List<String> c) {
    	
    	LogradouroTipo logradouroTipo = new LogradouroTipo();

    	logradouroTipo.setId(c.get(LGTP_ID_INDEX));
    	logradouroTipo.setDescricao(c.get(LGTP_DSLOGRADOUROTIPO_INDEX));
    	logradouroTipo.setDescricaoAbreviada(c.get(LGTP_DSABREVIADO_INDEX));
        
        return logradouroTipo;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(LogradouroTipoColunas.ID, getId());
		values.put(LogradouroTipoColunas.DESCRICAO, getDescricao());
		values.put(LogradouroTipoColunas.DESCRICAOABREV, getDescricaoAbreviada());

		return values;
	}
    
	public ArrayList<LogradouroTipo> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<LogradouroTipo> listaLogradouroTipo = new ArrayList<LogradouroTipo>();
		if ( cursor.moveToFirst() ) {
			
			do{
				int _codigo 	  		= cursor.getColumnIndex(LogradouroTipoColunas.ID);
				int _descricao 		    = cursor.getColumnIndex(LogradouroTipoColunas.DESCRICAO);
				int _descricaoAbreviada = cursor.getColumnIndex(LogradouroTipoColunas.DESCRICAOABREV);
				
				LogradouroTipo logradouroTipo = new LogradouroTipo();
				
				logradouroTipo.setId(cursor.getInt(_codigo));
				logradouroTipo.setDescricao(cursor.getString(_descricao));
				logradouroTipo.setDescricaoAbreviada(cursor.getString(_descricaoAbreviada));
				
				listaLogradouroTipo.add(logradouroTipo);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaLogradouroTipo;
	}
	
	public LogradouroTipo carregarEntidade(Cursor cursor) {		
		
		int _codigo 	  		= cursor.getColumnIndex(LogradouroTipoColunas.ID);
		int _descricao 		    = cursor.getColumnIndex(LogradouroTipoColunas.DESCRICAO);
		int _descricaoAbreviada = cursor.getColumnIndex(LogradouroTipoColunas.DESCRICAOABREV);
		
		LogradouroTipo logradouroTipo = new LogradouroTipo();

		if ( cursor.moveToFirst() ) {
			
			logradouroTipo.setId(cursor.getInt(_codigo));
			logradouroTipo.setDescricao(cursor.getString(_descricao));
			logradouroTipo.setDescricaoAbreviada(cursor.getString(_descricaoAbreviada));
		}

		cursor.close();
		return logradouroTipo;
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

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "LOGRADOURO_TIPO";
	}
}
