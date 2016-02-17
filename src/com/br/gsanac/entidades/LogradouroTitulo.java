package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto LogradouroTitulo
 * </p>
 * 
 * @author Anderson Cabral
 * @since  12/12/2012
 */
public class LogradouroTitulo extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int LGTP_ID_INDEX         		 = 1;
    private static final int LGTP_DSLOGRADOUROTIPO_INDEX = 2;
    private static final int LGTP_DSABREVIADO_INDEX 	 = 3;

    private String descricao;
    private String descricaoAbreviada;
    
    public static final String[] columns = new String[]{
    	LogradouroTituloColunas.ID,
    	LogradouroTituloColunas.DESCRICAO,
    	LogradouroTituloColunas.DESCRICAOABREV
    };
    
    public static final class LogradouroTituloColunas implements BaseColumns{
    	public static final String ID 		 	  = "LGTT_ID";
    	public static final String DESCRICAO 	  = "LGTT_DSLOGRADOUROTITULO";
    	public static final String DESCRICAOABREV = "LGTT_DSABREVIADO";
    }
	
    public final class LogradouroTituloColunasTipo {
		public final String ID 		  	   = " INTEGER PRIMARY KEY";
		public final String DESCRICAO 	   = " VARCHAR(25) NOT NULL";
		public final String DESCRICAOABREV = " VARCHAR(5)";
		
		private String[] tipos = new String[] {ID, DESCRICAO, DESCRICAOABREV};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static LogradouroTitulo inserirDoArquivo(List<String> c) {
    	
    	LogradouroTitulo logradouroTitulo = new LogradouroTitulo();

    	logradouroTitulo.setId(c.get(LGTP_ID_INDEX));
    	logradouroTitulo.setDescricao(c.get(LGTP_DSLOGRADOUROTIPO_INDEX));
    	logradouroTitulo.setDescricaoAbreviada(c.get(LGTP_DSABREVIADO_INDEX));
        
        return logradouroTitulo;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(LogradouroTituloColunas.ID, getId());
		values.put(LogradouroTituloColunas.DESCRICAO, getDescricao());
		values.put(LogradouroTituloColunas.DESCRICAOABREV, getDescricaoAbreviada());

		return values;
	}
    
	public ArrayList<LogradouroTitulo> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<LogradouroTitulo> listaLogradouroTitulo = new ArrayList<LogradouroTitulo>();
		if ( cursor.moveToFirst() ){
			
			do{
				int _codigo 	  		= cursor.getColumnIndex(LogradouroTituloColunas.ID);
				int _descricao 		    = cursor.getColumnIndex(LogradouroTituloColunas.DESCRICAO);
				int _descricaoAbreviada = cursor.getColumnIndex(LogradouroTituloColunas.DESCRICAOABREV);
				
				LogradouroTitulo logradouroTitulo = new LogradouroTitulo();
				
				logradouroTitulo.setId(cursor.getInt(_codigo));
				logradouroTitulo.setDescricao(cursor.getString(_descricao));
				logradouroTitulo.setDescricaoAbreviada(cursor.getString(_descricaoAbreviada));
				
				listaLogradouroTitulo.add(logradouroTitulo);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaLogradouroTitulo;
	}
	
	public LogradouroTitulo carregarEntidade(Cursor cursor) {		
		
		int _codigo 	  		= cursor.getColumnIndex(LogradouroTituloColunas.ID);
		int _descricao 		    = cursor.getColumnIndex(LogradouroTituloColunas.DESCRICAO);
		int _descricaoAbreviada = cursor.getColumnIndex(LogradouroTituloColunas.DESCRICAOABREV);
		
		LogradouroTitulo logradouroTitulo = new LogradouroTitulo();

		if ( cursor.moveToFirst() ) {
			
			logradouroTitulo.setId(cursor.getInt(_codigo));
			logradouroTitulo.setDescricao(cursor.getString(_descricao));
			logradouroTitulo.setDescricaoAbreviada(cursor.getString(_descricaoAbreviada));
		}

		cursor.close();
		return logradouroTitulo;
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
		return "LOGRADOURO_TITULO";
	}
}
