package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsavel pelo objeto LogradouroBairro
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class LogradouroBairro extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     LGBR_ID_INDEX        = 1;
    private static final int     BAIRRO_INDEX        = 2;
    private static final int     LOGRADOURO_INDEX        = 3;
    


    private Bairro bairro;
    
    private Logradouro logradouro;

    public static final String[] colunas              = new String[] {
    	LogradouroBairros.ID,
    	LogradouroBairros.BAIRRO,
    	LogradouroBairros.LOGRADOURO
    };

    public static final class LogradouroBairros implements BaseColumns {
        public static final String ID = "LGBR_ID";
        public static final String BAIRRO = "BAIR_ID";
        public static final String LOGRADOURO = "LOGR_ID";
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "LOGRADOURO_BAIRRO";
	}
    
    public final class LogradouroBairroTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String BAIRRO = " INTEGER NOT NULL";
		public final String LOGRADOURO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, BAIRRO, LOGRADOURO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static LogradouroBairro inserirDoArquivo(List<String> c) {
    	
    	LogradouroBairro logradouroBairro = new LogradouroBairro();

    	logradouroBairro.setId(c.get(LGBR_ID_INDEX));
    	
    	Bairro bairro = new Bairro();
    	bairro.setId(c.get(BAIRRO_INDEX));
    	logradouroBairro.setBairro(bairro);
    	
    	Logradouro logradouro = new Logradouro();
    	logradouro.setId(c.get(LOGRADOURO_INDEX));
    	logradouroBairro.setLogradouro(logradouro);
        
        return logradouroBairro;
    }

    
    public static LogradouroBairro inserirAtualizarDoArquivoDividido(List<String> c, Logradouro logradouro) {
    	
    	LogradouroBairro logradouroBairro = new LogradouroBairro();

//    	Logradouro logradouro = new Logradouro();
//    	logradouro.setId(c.get(AD_LOGRADOURO_INDEX));
    	logradouroBairro.setLogradouro(logradouro);
    	
    	Bairro bairro = new Bairro();
    	bairro.setId(c.get(BAIRRO_INDEX));
    	logradouroBairro.setBairro(bairro);
        
        return logradouroBairro;
    }

  
	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(LogradouroBairros.ID, getId());
		values.put(LogradouroBairros.BAIRRO, getBairro().getId());
		values.put(LogradouroBairros.LOGRADOURO, getLogradouro().getId());

		return values;
	}
	
	public ArrayList<LogradouroBairro> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<LogradouroBairro> logradouros = new ArrayList<LogradouroBairro>();
		
		if ( cursor.moveToFirst() ) {
			
			do{
				int codigo = cursor.getColumnIndex(LogradouroBairros.ID);
				int bairroIndex = cursor.getColumnIndex(LogradouroBairros.BAIRRO);
				int logradouroIndex = cursor.getColumnIndex(LogradouroBairros.LOGRADOURO);
				
				LogradouroBairro logradouroBairro = new LogradouroBairro();
				
				logradouroBairro.setId(cursor.getInt(codigo));
				
				Bairro bairro = new Bairro();
				bairro.setId(cursor.getInt(bairroIndex));
				logradouroBairro.setBairro(bairro);
				
				Logradouro logradouro = new Logradouro();
				logradouro.setId(cursor.getInt(logradouroIndex));
				logradouroBairro.setLogradouro(logradouro);
				
				logradouros.add(logradouroBairro);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return logradouros;
	}
	
	public LogradouroBairro carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(LogradouroBairros.ID);
		int bairroIndex = cursor.getColumnIndex(LogradouroBairros.BAIRRO);
		int logradouroIndex = cursor.getColumnIndex(LogradouroBairros.LOGRADOURO);
		
		LogradouroBairro logradouroBairro = new LogradouroBairro();

		if ( cursor.moveToFirst() ) {
			
			logradouroBairro.setId(cursor.getInt(codigo));
			
			Bairro bairro = new Bairro();
			bairro.setId(cursor.getInt(bairroIndex));
			logradouroBairro.setBairro(bairro);
			
			Logradouro logradouro = new Logradouro();
			logradouro.setId(cursor.getInt(logradouroIndex));
			logradouroBairro.setLogradouro(logradouro);
			
		}

		cursor.close();
		return logradouroBairro;
	}
}