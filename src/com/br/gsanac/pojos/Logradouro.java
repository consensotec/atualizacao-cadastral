package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.LogradouroTipo.LogradouroTipoColuna;
import com.br.gsanac.pojos.LogradouroTitulo.LogradouroTituloColuna;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 04/09/2014
 */
public class Logradouro extends EntidadeBasica {

    private static final long serialVersionUID = 4327285475536305741L;

    // Atributos
    private String nomeLogradouro;
    private String nomePopularLogradouro;
    private String nomeLoteamento;
    private Municipio municipio;
    private LogradouroTipo logradouroTipo;
    private LogradouroTitulo logradouroTitulo;
    private String codigoUnico;
    private Integer indicadorNovo;
    private Integer indicadorTransmitido;
    private Integer cepDesconhecido;
    
	// Index de acesso
    private static final int LOGR_ID_INDEX = 1;
    private static final int LOGR_NMLOGRADOURO_INDEX = 2;
    private static final int LOGR_NMPOPULAR_INDEX 	 = 3;
    private static final int LOGR_NMLOTEAMENTO_INDEX = 4;
    private static final int MUNI_ID_INDEX = 5;
    private static final int LGTP_ID_INDEX = 6;
    private static final int LGTT_ID_INDEX = 7;
    //private static final int LOGR_ICCEPDESC_INDEX = 8;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class LogradouroColuna implements BaseColumns {
    	public static final String ID = "LOGR_ID";
    	public static final String NOMELOGRADOURO = "LOGR_NMLOGRADOURO";
    	public static final String NOMEPOPULARLOGRA = "LOGR_NMPOPULAR";
    	public static final String NOMELOTEAMENTO = "LOGR_NMLOTEAMENTO"; 
    	public static final String MUNICIPIO_ID = "MUNI_ID";
    	public static final String LOGRADOUROTIPO_ID = "LGTP_ID";
    	public static final String LOGRADOUROTITULO_ID = "LGTT_ID";
    	public static final String CODIGO_UNICO= "LOGR_CDUNICO";
    	public static final String INDICADORNOVO = "LOGR_ICNOVO"; 
    	public static final String INDICADOR_TRANSMITIDO= "LOGR_ICTRANSMITIDO";
    	public static final String ICCEPDESC = "LOGR_ICCEPDESC";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[] {
    	LogradouroColuna.ID,
    	LogradouroColuna.NOMELOGRADOURO,
    	LogradouroColuna.NOMEPOPULARLOGRA,
    	LogradouroColuna.NOMELOTEAMENTO,
    	LogradouroColuna.MUNICIPIO_ID,
    	LogradouroColuna.LOGRADOUROTIPO_ID,
    	LogradouroColuna.LOGRADOUROTITULO_ID,
    	LogradouroColuna.CODIGO_UNICO,
    	LogradouroColuna.INDICADORNOVO,
    	LogradouroColuna.INDICADOR_TRANSMITIDO,
    	LogradouroColuna.ICCEPDESC
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class LogradouroColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String NOMELOGRADOURO = " VARCHAR(40)";
		private final String NOMEPOPULARLOGRA = " VARCHAR(30)";
		private final String NOMELOTEAMENTO = " VARCHAR(30)";
		private final String MUNICIPIO_ID = " INTEGER";
		private final String LOGRADOUROTIPO_ID = " INTEGER";
		private final String LOGRADOUROTITULO_ID = " INTEGER";
		private final String CODIGO_UNICO = " VARCHAR(20) ";
		private final String INDICADORNOVO = " INTEGER";
		private final String INDICADOR_TRANSMITIDO= " INTEGER";
		private final String CEP_DESCONHECIDO = " INTEGER";
		
		private String[] tipos = new String[] {
				ID,
				NOMELOGRADOURO,
				NOMEPOPULARLOGRA,
				NOMELOTEAMENTO,
				MUNICIPIO_ID,
				LOGRADOUROTIPO_ID,
				LOGRADOUROTITULO_ID,
				CODIGO_UNICO,
				INDICADORNOVO,
				INDICADOR_TRANSMITIDO,
				CEP_DESCONHECIDO,
		};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

	@Override
    public String toString() {
    	//O código abaixo está sendo feito por que o sistema não possui nem eagger e lazy load. Os objetos
    	//filhos de logradouro estão vindo carregados apenas com o id.
    	
    	Fachada fachada = Fachada.getInstancia(null);
    	String result = "";
    	
    	if(getLogradouroTipo()!=null){    		
    		String[] paramsLogradouroTipo = {String.valueOf(getLogradouroTipo().getId())};
    		LogradouroTipo tmpLogradouroTipo = fachada.pesquisarObjetoGenerico(LogradouroTipo.class, LogradouroTipoColuna.ID+"=?", paramsLogradouroTipo, null, null);
    		this.setLogradouroTipo(tmpLogradouroTipo);
    		if(this.getLogradouroTipo()!=null){
    			result+=this.getLogradouroTipo().getDescricao()+" ";
    		}
    	}
    	
    	if(getLogradouroTitulo()!=null){    		
    		String[] paramsLogradouroTitulo = {String.valueOf(getLogradouroTitulo().getId())};
    		LogradouroTitulo tmpLogradouroTitulo = fachada.pesquisarObjetoGenerico(LogradouroTitulo.class, LogradouroTituloColuna.ID+"=?", paramsLogradouroTitulo, null, null);
    		this.setLogradouroTitulo(tmpLogradouroTitulo);
    		if(this.getLogradouroTitulo()!=null){
    			result+=this.getLogradouroTitulo().getDescricao()+" ";
    		}
    	}
    	return result + this.getNomeLogradouro();
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
	
    public Integer getCepDesconhecido() {
		return cepDesconhecido;
	}

	public void setCepDesconhecido(Integer cepDesconhecido) {
		this.cepDesconhecido = cepDesconhecido;
	}
    
	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static Logradouro converteLinhaArquivoEmObjeto(List<String> c) {
    	
    	Logradouro logradouro = new Logradouro();

    	logradouro.setId(Utilitarios.verificarNuloInt(c.get(LOGR_ID_INDEX)));
    	
        logradouro.setNomeLogradouro(c.get(LOGR_NMLOGRADOURO_INDEX));
        logradouro.setNomePopularLogradouro(c.get(LOGR_NMPOPULAR_INDEX));
        logradouro.setNomeLoteamento(c.get(LOGR_NMLOTEAMENTO_INDEX));       
        
        Municipio municipio = new Municipio();
    	municipio.setId(c.get(MUNI_ID_INDEX));
    	logradouro.setMunicipio(municipio);
    	
        LogradouroTipo logradouroTipo = new LogradouroTipo();
        logradouroTipo.setId(c.get(LGTP_ID_INDEX));
        logradouro.setLogradouroTipo(logradouroTipo);
        
        LogradouroTitulo logradouroTitulo = new LogradouroTitulo();
        logradouroTitulo.setId(Utilitarios.verificarNuloInt(c.get(LGTT_ID_INDEX)));
        logradouro.setLogradouroTitulo(logradouroTitulo);
        
        logradouro.setCodigoUnico("");
        logradouro.setIndicadorNovo(ConstantesSistema.NAO);
        logradouro.setIndicadorTransmitido(ConstantesSistema.NAO);
        
        //no gsan no é feito nenhum cadastro do indicador de cep desconhecido. nesse caso
        //todos os logradouros que vieram do roteiro não são logradouros de cep desconhecido.
        logradouro.setCepDesconhecido(ConstantesSistema.NAO);
        
        return logradouro;
    }    
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(LogradouroColuna.ID, getId());
		values.put(LogradouroColuna.MUNICIPIO_ID, getMunicipio().getId());
		values.put(LogradouroColuna.LOGRADOUROTIPO_ID, getLogradouroTipo().getId());
		
		if ( getLogradouroTitulo() != null && getLogradouroTitulo().getId() != null ) {
			values.put(LogradouroColuna.LOGRADOUROTITULO_ID, getLogradouroTitulo().getId());
		}
		
		values.put(LogradouroColuna.NOMELOGRADOURO, getNomeLogradouro());
		values.put(LogradouroColuna.NOMEPOPULARLOGRA, getNomePopularLogradouro());
		values.put(LogradouroColuna.NOMELOTEAMENTO, getNomeLoteamento());
		values.put(LogradouroColuna.INDICADORNOVO, getIndicadorNovo());
		values.put(LogradouroColuna.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());
		values.put(LogradouroColuna.CODIGO_UNICO, getCodigoUnico());
		values.put(LogradouroColuna.ICCEPDESC, getCepDesconhecido());
		return values;
	}
	
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<Logradouro> carregarListaEntidade(Cursor cursor) {		
		ArrayList<Logradouro> logradouros = new ArrayList<Logradouro>();
		
		if ( cursor.moveToFirst() ) {
		
			do{			
				int _id 	  	= cursor.getColumnIndex(LogradouroColuna.ID);
				int _municipioId = cursor.getColumnIndex(LogradouroColuna.MUNICIPIO_ID);
				int _logradouroTipoId = cursor.getColumnIndex(LogradouroColuna.LOGRADOUROTIPO_ID);
				int _logradouroTituloId = cursor.getColumnIndex(LogradouroColuna.LOGRADOUROTITULO_ID);
				int _nomeLogradouro = cursor.getColumnIndex(LogradouroColuna.NOMELOGRADOURO);
				int _nomePopularLogradouro = cursor.getColumnIndex(LogradouroColuna.NOMEPOPULARLOGRA);
				int _nomeLoteamento = cursor.getColumnIndex(LogradouroColuna.NOMELOTEAMENTO);
				int _indicadorNovo = cursor.getColumnIndex(LogradouroColuna.INDICADORNOVO);
				int _indicadorTransmitido = cursor.getColumnIndex(LogradouroColuna.INDICADOR_TRANSMITIDO);
				int _codigoUnico = cursor.getColumnIndex(LogradouroColuna.CODIGO_UNICO);
				int _cepDesconhecido = cursor.getColumnIndex(LogradouroColuna.ICCEPDESC);
				
				Logradouro logradouro = new Logradouro();
				
				logradouro.setId(cursor.getInt(_id));
			    
		        logradouro.setNomeLogradouro(cursor.getString(_nomeLogradouro));
		        logradouro.setNomePopularLogradouro(cursor.getString(_nomePopularLogradouro));
		        logradouro.setNomeLoteamento(cursor.getString(_nomeLoteamento)); 
		        
		        Municipio municipio = new Municipio();
		    	municipio.setId(cursor.getInt(_municipioId));
		    	logradouro.setMunicipio(municipio);
		    	
		        LogradouroTipo logradouroTipo = new LogradouroTipo();
		        logradouroTipo.setId(cursor.getInt(_logradouroTipoId));
		        logradouro.setLogradouroTipo(logradouroTipo);
		        
		        LogradouroTitulo logradouroTitulo = new LogradouroTitulo();
		        logradouroTitulo.setId(cursor.getInt(_logradouroTituloId));
		        logradouro.setLogradouroTitulo(logradouroTitulo);
		        
		        logradouro.setCodigoUnico(cursor.getString(_codigoUnico));
		        logradouro.setIndicadorNovo(cursor.getInt(_indicadorNovo)); 
		        logradouro.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido)); 
		       
		        logradouro.setCepDesconhecido(cursor.getInt(_cepDesconhecido));
		        
				logradouros.add(logradouro);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return logradouros;
	}
	
	// Método converte um cursor em  objeto
	public Logradouro carregarEntidade(Cursor cursor) {		
		
		int _id 	  	= cursor.getColumnIndex(LogradouroColuna.ID);
		int _municipioId = cursor.getColumnIndex(LogradouroColuna.MUNICIPIO_ID);
		int _logradouroTipoId = cursor.getColumnIndex(LogradouroColuna.LOGRADOUROTIPO_ID);
		int _logradouroTituloId = cursor.getColumnIndex(LogradouroColuna.LOGRADOUROTITULO_ID);
		int _nomeLogradouro = cursor.getColumnIndex(LogradouroColuna.NOMELOGRADOURO);
		int _nomePopularLogradouro = cursor.getColumnIndex(LogradouroColuna.NOMEPOPULARLOGRA);
		int _nomeLoteamento = cursor.getColumnIndex(LogradouroColuna.NOMELOTEAMENTO);
		int _indicadorNovo = cursor.getColumnIndex(LogradouroColuna.INDICADORNOVO);
		int _indicadorTransmitido = cursor.getColumnIndex(LogradouroColuna.INDICADOR_TRANSMITIDO);
		int _codigoUnico = cursor.getColumnIndex(LogradouroColuna.CODIGO_UNICO);
		int _cepDesconhecido = cursor.getColumnIndex(LogradouroColuna.ICCEPDESC);
	
		Logradouro logradouro = new Logradouro();

		if ( cursor.moveToFirst() ) {
			
			logradouro.setId(cursor.getInt(_id));
			
			logradouro.setNomeLogradouro(cursor.getString(_nomeLogradouro));
	        logradouro.setNomePopularLogradouro(cursor.getString(_nomePopularLogradouro));
	        logradouro.setNomeLoteamento(cursor.getString(_nomeLoteamento));
	        
	        Municipio municipio = new Municipio();
	    	municipio.setId(cursor.getInt(_municipioId));
	    	logradouro.setMunicipio(municipio);
	    	
	        LogradouroTipo logradouroTipo = new LogradouroTipo();
	        logradouroTipo.setId(cursor.getInt(_logradouroTipoId));
	        logradouro.setLogradouroTipo(logradouroTipo);
	        
	        LogradouroTitulo logradouroTitulo = new LogradouroTitulo();
	        logradouroTitulo.setId(cursor.getInt(_logradouroTituloId));
	        logradouro.setLogradouroTitulo(logradouroTitulo);
	        
	        logradouro.setCodigoUnico(cursor.getString(_codigoUnico));
	        logradouro.setIndicadorNovo(cursor.getInt(_indicadorNovo)); 
	        logradouro.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido)); 
	        logradouro.setCepDesconhecido(cursor.getInt(_cepDesconhecido));
	  }
		cursor.close();
		return logradouro;
	}

	// Retorna no da tabela
	public String getNomeTabela(){
		return "LOGRADOURO";
	}
	
}