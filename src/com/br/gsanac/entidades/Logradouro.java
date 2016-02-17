package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsavel pelo objeto Logradouro
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class Logradouro extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     LOGR_ID_INDEX = 1;
    private static final int     MUNI_ID_INDEX = 5;
    private static final int     LGTP_ID_INDEX = 6;
    private static final int     LGTT_ID_INDEX = 7;
    private static final int     LOGR_NMLOGRADOURO_INDEX = 2;
    private static final int     LOGR_NMPOPULAR_INDEX 	 = 3;
    private static final int     LOGR_NMLOTEAMENTO_INDEX = 4;

    private Municipio municipio;
    private LogradouroTipo logradouroTipo;
    private LogradouroTitulo logradouroTitulo;
    private String nomeLogradouro;
    private String nomePopularLogradouro;
    private String nomeLoteamento;
    private Integer indicadorNovo;
    private Integer indicadorTransmitido;
    private String codigoUnico;

    public static final String[] colunas = new String[] {
    	Logradouros.ID,
    	Logradouros.MUNICIPIO_ID,
    	Logradouros.LOGRADOUROTIPO_ID,
    	Logradouros.LOGRADOUROTITULO_ID,
    	Logradouros.NOMELOGRADOURO,
    	Logradouros.NOMEPOPULARLOGRA,
    	Logradouros.NOMELOTEAMENTO,
    	Logradouros.INDICADORNOVO,
    	Logradouros.INDICADOR_TRANSMITIDO,
    	Logradouros.CODIGO_UNICO
    	
    };

    public static final class Logradouros implements BaseColumns {
        public static final String ID = "LOGR_ID";
        public static final String MUNICIPIO_ID 	   = "MUNI_ID";
        public static final String LOGRADOUROTIPO_ID   = "LGTP_ID";
        public static final String LOGRADOUROTITULO_ID = "LGTT_ID";
        public static final String NOMELOGRADOURO 	= "LOGR_NMLOGRADOURO";
        public static final String NOMEPOPULARLOGRA = "LOGR_NMPOPULAR";
        public static final String NOMELOTEAMENTO 	= "LOGR_NMLOTEAMENTO"; 
        public static final String INDICADORNOVO 	= "LOGR_ICNOVO"; 
        public static final String INDICADOR_TRANSMITIDO= "LOGR_ICTRANSMITIDO";
        public static final String CODIGO_UNICO= "LOGR_CDUNICO";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "LOGRADOURO";
	}
    
    public final class LogradouroTipos {
		public final String ID 		  			= " INTEGER PRIMARY KEY";
		public final String MUNICIPIO_ID 		= " INTEGER";
		public final String LOGRADOUROTIPO_ID 	= " INTEGER";
		public final String LOGRADOUROTITULO_ID = " INTEGER";
		public final String NOMELOGRADOURO 		= " VARCHAR(40)";
		public final String NOMEPOPULARLOGRA 	= " VARCHAR(30)";
		public final String NOMELOTEAMENTO 	 	= " VARCHAR(30)";
		public final String INDICADORNOVO 	 	= " INTEGER";
		public final String INDICADOR_TRANSMITIDO= " INTEGER";
		public final String CODIGO_UNICO = " VARCHAR(20) ";
		
		private String[] tipos = new String[] {ID, MUNICIPIO_ID, LOGRADOUROTIPO_ID, LOGRADOUROTITULO_ID,
												NOMELOGRADOURO, NOMEPOPULARLOGRA, NOMELOTEAMENTO, INDICADORNOVO, 
												INDICADOR_TRANSMITIDO, CODIGO_UNICO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static Logradouro inserirDoArquivo(List<String> c) {
    	
    	Logradouro logradouro = new Logradouro();

    	logradouro.setId(Util.verificarNuloInt(c.get(LOGR_ID_INDEX)));
    	
    	Municipio _municipio = new Municipio();
    	_municipio.setId(c.get(MUNI_ID_INDEX));
    	logradouro.setMunicipio(_municipio);
    	
        LogradouroTipo _logradouroTipo = new LogradouroTipo();
        _logradouroTipo.setId(c.get(LGTP_ID_INDEX));
        logradouro.setLogradouroTipo(_logradouroTipo);
        
        LogradouroTitulo _logradouroTitulo = new LogradouroTitulo();
        _logradouroTitulo.setId(Util.verificarNuloInt(c.get(LGTT_ID_INDEX)));
        logradouro.setLogradouroTitulo(_logradouroTitulo);
        logradouro.setNomeLogradouro(c.get(LOGR_NMLOGRADOURO_INDEX));
        logradouro.setNomePopularLogradouro(c.get(LOGR_NMPOPULAR_INDEX));
        logradouro.setNomeLoteamento(c.get(LOGR_NMLOTEAMENTO_INDEX));       
        logradouro.setIndicadorNovo(ConstantesSistema.NAO);
        logradouro.setIndicadorTransmitido(ConstantesSistema.NAO);
        logradouro.setCodigoUnico("");
        
        return logradouro;
    }    
    
    
 public static Logradouro inserirAtualizarDoArquivoDividido(List<String> c) {
    	
    	Logradouro logradouro = new Logradouro();

//    	logradouro.setId()
    	logradouro.setCodigoUnico(c.get(LOGR_ID_INDEX));
    	logradouro.setNomeLogradouro(c.get(LOGR_NMLOGRADOURO_INDEX));
        logradouro.setNomePopularLogradouro(c.get(LOGR_NMPOPULAR_INDEX));
        logradouro.setNomeLoteamento(c.get(LOGR_NMLOTEAMENTO_INDEX));
    	
    	Municipio _municipio = new Municipio();
    	_municipio.setId(c.get(MUNI_ID_INDEX));
    	logradouro.setMunicipio(_municipio);
    	
        LogradouroTipo _logradouroTipo = new LogradouroTipo();
        _logradouroTipo.setId(c.get(LGTP_ID_INDEX));
        logradouro.setLogradouroTipo(_logradouroTipo);
        
        LogradouroTitulo _logradouroTitulo = new LogradouroTitulo();
        _logradouroTitulo.setId(Util.verificarNuloInt(c.get(LGTT_ID_INDEX)));
        logradouro.setLogradouroTitulo(_logradouroTitulo);
              
        logradouro.setIndicadorNovo(ConstantesSistema.SIM);
        logradouro.setIndicadorTransmitido(ConstantesSistema.NAO);
        
        
        return logradouro;
    }   
  
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(Logradouros.ID, getId());
		values.put(Logradouros.MUNICIPIO_ID, getMunicipio().getId());
		values.put(Logradouros.LOGRADOUROTIPO_ID, getLogradouroTipo().getId());
		
		if ( getLogradouroTitulo() != null && getLogradouroTitulo().getId() != null ) {
			values.put(Logradouros.LOGRADOUROTITULO_ID, getLogradouroTitulo().getId());
		}
		values.put(Logradouros.NOMELOGRADOURO, getNomeLogradouro());
		values.put(Logradouros.NOMEPOPULARLOGRA, getNomePopularLogradouro());
		values.put(Logradouros.NOMELOTEAMENTO, getNomeLoteamento());
		values.put(Logradouros.INDICADORNOVO, getIndicadorNovo());
		values.put(Logradouros.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());
		values.put(Logradouros.CODIGO_UNICO, getCodigoUnico());

		return values;
	}
	
	public ArrayList<Logradouro> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<Logradouro> logradouros = new ArrayList<Logradouro>();
		
		if ( cursor.moveToFirst() ) {
		
			do{			
				int codigo 	  	= cursor.getColumnIndex(Logradouros.ID);
				int municipioId = cursor.getColumnIndex(Logradouros.MUNICIPIO_ID);
				int logradouroTipoId = cursor.getColumnIndex(Logradouros.LOGRADOUROTIPO_ID);
				int logradouroTituloId = cursor.getColumnIndex(Logradouros.LOGRADOUROTITULO_ID);
				int nomeLogradouro = cursor.getColumnIndex(Logradouros.NOMELOGRADOURO);
				int nomePopularLogradouro = cursor.getColumnIndex(Logradouros.NOMEPOPULARLOGRA);
				int nomeLoteamento = cursor.getColumnIndex(Logradouros.NOMELOTEAMENTO);
				int indicadorNovo = cursor.getColumnIndex(Logradouros.INDICADORNOVO);
				int indicadorTransmitido = cursor.getColumnIndex(Logradouros.INDICADOR_TRANSMITIDO);
				int codigoUnico = cursor.getColumnIndex(Logradouros.CODIGO_UNICO);
				
				Logradouro logradouro = new Logradouro();
				
				logradouro.setId(cursor.getInt(codigo));
				
		    	Municipio _municipio = new Municipio();
		    	_municipio.setId(cursor.getInt(municipioId));
		    	logradouro.setMunicipio(_municipio);
		    	
		        LogradouroTipo _logradouroTipo = new LogradouroTipo();
		        _logradouroTipo.setId(cursor.getInt(logradouroTipoId));
		        logradouro.setLogradouroTipo(_logradouroTipo);
		        
		        LogradouroTitulo _logradouroTitulo = new LogradouroTitulo();
		        _logradouroTitulo.setId(cursor.getInt(logradouroTituloId));
		        logradouro.setLogradouroTitulo(_logradouroTitulo);
		        
		        logradouro.setNomeLogradouro(cursor.getString(nomeLogradouro));
		        logradouro.setNomePopularLogradouro(cursor.getString(nomePopularLogradouro));
		        logradouro.setNomeLoteamento(cursor.getString(nomeLoteamento)); 
		        logradouro.setIndicadorNovo(cursor.getInt(indicadorNovo)); 
		        logradouro.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido)); 
		        logradouro.setCodigoUnico(cursor.getString(codigoUnico));
				
				logradouros.add(logradouro);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return logradouros;
	}
	
	public Logradouro carregarEntidade(Cursor cursor) {		
		
		int codigo 	  	= cursor.getColumnIndex(Logradouros.ID);
		int municipioId = cursor.getColumnIndex(Logradouros.MUNICIPIO_ID);
		int logradouroTipoId = cursor.getColumnIndex(Logradouros.LOGRADOUROTIPO_ID);
		int logradouroTituloId = cursor.getColumnIndex(Logradouros.LOGRADOUROTITULO_ID);
		int nomeLogradouro = cursor.getColumnIndex(Logradouros.NOMELOGRADOURO);
		int nomePopularLogradouro = cursor.getColumnIndex(Logradouros.NOMEPOPULARLOGRA);
		int nomeLoteamento = cursor.getColumnIndex(Logradouros.NOMELOTEAMENTO);
		int indicadorNovo = cursor.getColumnIndex(Logradouros.INDICADORNOVO);
		int indicadorTransmitido = cursor.getColumnIndex(Logradouros.INDICADOR_TRANSMITIDO);
		int codigoUnico = cursor.getColumnIndex(Logradouros.CODIGO_UNICO);
	
		Logradouro logradouro = new Logradouro();

		if ( cursor.moveToFirst() ) {
			
			logradouro.setId(cursor.getInt(codigo));
			
	    	Municipio _municipio = new Municipio();
	    	_municipio.setId(cursor.getInt(municipioId));
	    	logradouro.setMunicipio(_municipio);
	    	
	        LogradouroTipo _logradouroTipo = new LogradouroTipo();
	        _logradouroTipo.setId(cursor.getInt(logradouroTipoId));
	        logradouro.setLogradouroTipo(_logradouroTipo);
	        
	        LogradouroTitulo _logradouroTitulo = new LogradouroTitulo();
	        _logradouroTitulo.setId(cursor.getInt(logradouroTituloId));
	        logradouro.setLogradouroTitulo(_logradouroTitulo);
	        
	        logradouro.setNomeLogradouro(cursor.getString(nomeLogradouro));
	        logradouro.setNomePopularLogradouro(cursor.getString(nomePopularLogradouro));
	        logradouro.setNomeLoteamento(cursor.getString(nomeLoteamento)); 
	        logradouro.setIndicadorNovo(cursor.getInt(indicadorNovo)); 
	        logradouro.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido)); 
	        logradouro.setCodigoUnico(cursor.getString(codigoUnico));
		}
		cursor.close();
		return logradouro;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public LogradouroTipo getLogradouroTipo() {
		return logradouroTipo;
	}

	public void setLogradouroTipo(LogradouroTipo logradouroTipo) {
		this.logradouroTipo = logradouroTipo;
	}

	public LogradouroTitulo getLogradouroTitulo() {
		return logradouroTitulo;
	}

	public void setLogradouroTitulo(LogradouroTitulo logradouroTitulo) {
		this.logradouroTitulo = logradouroTitulo;
	}

	public String getNomeLogradouro() {
		return nomeLogradouro;
	}

	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}

	public String getNomePopularLogradouro() {
		return nomePopularLogradouro;
	}

	public void setNomePopularLogradouro(String nomePopularLogradouro) {
		this.nomePopularLogradouro = nomePopularLogradouro;
	}

	public String getNomeLoteamento() {
		return nomeLoteamento;
	}

	public void setNomeLoteamento(String nomeLoteamento) {
		this.nomeLoteamento = nomeLoteamento;
	}

	public Integer getIndicadorNovo() {
		return indicadorNovo;
	}

	public void setIndicadorNovo(Integer indicadorNovo) {
		this.indicadorNovo = indicadorNovo;
	}

	public Integer getIndicadorTransmitido() {
		return indicadorTransmitido;
	}

	public void setIndicadorTransmitido(Integer indicadorTransmitido) {
		this.indicadorTransmitido = indicadorTransmitido;
	}

	public String getCodigoUnico() {
		return codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}
	
}