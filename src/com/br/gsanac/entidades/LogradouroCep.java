package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.gsanac.util.ConstantesSistema;


/**
 * <p>
 * Classe responsável pelo objeto LogradouroCep
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class LogradouroCep extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int LGCP_ID_INDEX    = 1;
    private static final int CEP_INDEX        = 2;
    private static final int LOGRADOURO_INDEX  = 3;

    private Cep cep;    
    private Logradouro logradouro;
    private Integer indicadorNovo;
    private Integer indicadorTransmitido;

    public static final String[] colunas = new String[] {
    	LogradouroCeps.ID,
    	LogradouroCeps.CEP,
    	LogradouroCeps.LOGRADOURO,
    	LogradouroCeps.INDICADORNOVO,
    	LogradouroCeps.INDICADOR_TRANSMITIDO
    };

    public static final class LogradouroCeps implements BaseColumns {
        public static final String ID = "LGCP_ID";
        public static final String CEP = "CEP_ID";
        public static final String LOGRADOURO = "LOGR_ID";
        public static final String INDICADORNOVO = "LGCP_ICNOVO";
        public static final String INDICADOR_TRANSMITIDO = "LGCP_ICTRANSMITIDO";
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "LOGRADOURO_CEP";
	}
    
    public final class LogradouroCepTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String CEP = " INTEGER NOT NULL";
		public final String LOGRADOURO = " INTEGER NOT NULL";
		public final String INDICADORNOVO = " INTEGER NOT NULL";
		public final String INDICADOR_TRANSMITIDO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, CEP, LOGRADOURO, INDICADORNOVO, INDICADOR_TRANSMITIDO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static LogradouroCep inserirDoArquivo(List<String> c) {
    	
    	LogradouroCep logradouroCep = new LogradouroCep();

    	logradouroCep.setId(c.get(LGCP_ID_INDEX));
    	
    	Cep cep = new Cep();
    	cep.setId(c.get(CEP_INDEX));
    	logradouroCep.setCep(cep);
    	Logradouro logradouro = new Logradouro();
    	logradouro.setId(c.get(LOGRADOURO_INDEX));
    	logradouroCep.setLogradouro(logradouro);
    	logradouroCep.setIndicadorNovo(ConstantesSistema.NAO);
    	logradouroCep.setIndicadorTransmitido(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO);
        
        return logradouroCep;
    }
    
    
 public static LogradouroCep inserirAtualizarDoArquivoDividido(List<String> c, Logradouro logradouro, Cep cep) {
    	
    	LogradouroCep logradouroCep = new LogradouroCep();

    	logradouroCep.setCep(cep);
    	
    	logradouroCep.setLogradouro(logradouro);
    	
    	logradouroCep.setIndicadorNovo(ConstantesSistema.SIM);
    	logradouroCep.setIndicadorTransmitido(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO);
        
        return logradouroCep;
    }


  
    public Integer getIndicadorTransmitido() {
		return indicadorTransmitido;
	}

	public void setIndicadorTransmitido(Integer indicadorTransmitido) {
		this.indicadorTransmitido = indicadorTransmitido;
	}

	public Cep getCep() {
		return cep;
	}

	public void setCep(Cep cep) {
		this.cep = cep;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getIndicadorNovo() {
		return indicadorNovo;
	}

	public void setIndicadorNovo(Integer indicadorNovo) {
		this.indicadorNovo = indicadorNovo;
	}

	public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(LogradouroCeps.ID, getId());
		values.put(LogradouroCeps.CEP, getCep().getId());
		values.put(LogradouroCeps.LOGRADOURO, getLogradouro().getId());
		values.put(LogradouroCeps.INDICADORNOVO, getIndicadorNovo());
		values.put(LogradouroCeps.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());

		return values;
	}
	
	public ArrayList<LogradouroCep> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<LogradouroCep> logradouros = new ArrayList<LogradouroCep>();
		if ( cursor.moveToFirst() ) {
		
			do{			
				int codigo = cursor.getColumnIndex(LogradouroCeps.ID);
				int cepIndex = cursor.getColumnIndex(LogradouroCeps.CEP);
				int logradouroIndex = cursor.getColumnIndex(LogradouroCeps.LOGRADOURO);
				int indicadorNovoIndex = cursor.getColumnIndex(LogradouroCeps.INDICADORNOVO);
				int indicadorTransmitidoIndex = cursor.getColumnIndex(LogradouroCeps.INDICADOR_TRANSMITIDO);
				
				LogradouroCep logradouroCep = new LogradouroCep();
				
				logradouroCep.setId(cursor.getInt(codigo));
				
				Cep cep = new Cep();
				cep.setId(cursor.getInt(cepIndex));
				logradouroCep.setCep(cep);
				
				Logradouro logradouro = new Logradouro();
				logradouro.setId(cursor.getInt(logradouroIndex));
				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setIndicadorNovo(cursor.getInt(indicadorNovoIndex));
				logradouroCep.setIndicadorTransmitido(cursor.getInt(indicadorTransmitidoIndex));
				
				logradouros.add(logradouroCep);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return logradouros;
	}
	
	public LogradouroCep carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(LogradouroCeps.ID);
		int cepIndex = cursor.getColumnIndex(LogradouroCeps.CEP);
		int logradouroIndex = cursor.getColumnIndex(LogradouroCeps.LOGRADOURO);
		int indicadorNovoIndex = cursor.getColumnIndex(LogradouroCeps.INDICADORNOVO);
		int indicadorTransmitidoIndex = cursor.getColumnIndex(LogradouroCeps.INDICADOR_TRANSMITIDO);
		
		LogradouroCep logradouroCep = new LogradouroCep();

		if ( cursor.moveToFirst() ) {
			
			logradouroCep.setId(cursor.getInt(codigo));
			
			Cep cep = new Cep();
			cep.setId(cursor.getInt(cepIndex));
			logradouroCep.setCep(cep);
			
			Logradouro logradouro = new Logradouro();
			logradouro.setId(cursor.getInt(logradouroIndex));
			logradouroCep.setLogradouro(logradouro);
			logradouroCep.setIndicadorNovo(cursor.getInt(indicadorNovoIndex));
			logradouroCep.setIndicadorTransmitido(cursor.getInt(indicadorTransmitidoIndex));
			
		}

		cursor.close();
		return logradouroCep;
	}
}