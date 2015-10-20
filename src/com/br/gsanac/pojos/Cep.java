package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;




import com.br.gsanac.utilitarios.ConstantesSistema;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class Cep extends EntidadeBasica {

    private static final long serialVersionUID = 4327285475536305741L;

    // Atributos
    private Integer codigo;
    private String codigoUnico;
    private Integer indicadorNovo;
    private Integer indicadorTransmitido;
    
    // Index de acesso
    private static final int CEP_ID_INDEX     = 1;
    private static final int CEP_CODIGO_INDEX = 2;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class CepColuna implements BaseColumns {
    	public static final String ID = "CEP_ID";
    	public static final String CODIGO = "CEP_CDCEP";
    	public static final String CODIGO_UNICO = "CEP_CDUNICO";
    	public static final String INDICADORNOVO = "CEP_ICNOVO";
    	public static final String INDICADOR_TRANSMITIDO = "CEP_ICTRANSMITIDO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[] {
    	CepColuna.ID,
    	CepColuna.CODIGO,
    	CepColuna.CODIGO_UNICO,
    	CepColuna.INDICADORNOVO,
    	CepColuna.INDICADOR_TRANSMITIDO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class CepColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String CODIGO = " INTEGER NOT NULL";
		private final String CODIGO_UNICO = " VARCHAR(20) NULL ";
		private final String INDICADORNOVO = " INTEGER NOT NULL";
		private final String INDICADOR_TRANSMITIDO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, CODIGO,CODIGO_UNICO, INDICADORNOVO, INDICADOR_TRANSMITIDO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public Integer getIndicadorTransmitido() {
		return indicadorTransmitido;
	}

	public void setIndicadorTransmitido(Integer indicadorTransmitido) {
		this.indicadorTransmitido = indicadorTransmitido;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
  
    public Integer getIndicadorNovo() {
		return indicadorNovo;
	}

	public void setIndicadorNovo(Integer indicadorNovo) {
		this.indicadorNovo = indicadorNovo;
	}
	
	public String getCodigoUnico() {
		return codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

    
    /*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static Cep converteLinhaArquivoEmObjeto(List<String> c) {
    	Cep cep = new Cep();
    	cep.setId(c.get(CEP_ID_INDEX));
    	cep.setCodigo(Integer.valueOf(c.get(CEP_CODIGO_INDEX)));
    	cep.setIndicadorNovo(ConstantesSistema.NAO);
    	cep.setIndicadorTransmitido(ConstantesSistema.NAO);

    	return cep;
    }
   
	// Método retorna ContentValues
	public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(CepColuna.ID, getId());
		values.put(CepColuna.CODIGO, getCodigo());
		values.put(CepColuna.CODIGO_UNICO, getCodigoUnico());
		values.put(CepColuna.INDICADORNOVO, getIndicadorNovo());
		values.put(CepColuna.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());
		return values;
	}
	
	/*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<Cep> carregarListaEntidade(Cursor cursor) {		
		ArrayList<Cep> ceps = new ArrayList<Cep>();
		if ( cursor.moveToFirst() ) {
			do{
				Cep cep = new Cep();
				int _id = cursor.getColumnIndex(CepColuna.ID);
				int _codigo = cursor.getColumnIndex(CepColuna.CODIGO);
				int _codigoUnico = cursor.getColumnIndex(CepColuna.CODIGO_UNICO);
				int _indicadorNovo = cursor.getColumnIndex(CepColuna.INDICADORNOVO);
				int _indicadorTransmitido = cursor.getColumnIndex(CepColuna.INDICADOR_TRANSMITIDO);
				
				cep.setId(cursor.getInt(_id));
				cep.setCodigo(cursor.getInt(_codigo));
				cep.setIndicadorNovo(cursor.getInt(_indicadorNovo));
				cep.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido));
				cep.setCodigoUnico(cursor.getString(_codigoUnico));
				
				ceps.add(cep);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return ceps;
	}
	
	// Método converte um cursor em  objeto
	public Cep carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(CepColuna.ID);
		int _codigo = cursor.getColumnIndex(CepColuna.CODIGO);
		int _codigoUnico = cursor.getColumnIndex(CepColuna.CODIGO_UNICO);
		int _indicadorNovo = cursor.getColumnIndex(CepColuna.INDICADORNOVO);
		int _indicadorTransmitido = cursor.getColumnIndex(CepColuna.INDICADOR_TRANSMITIDO);
		
		Cep cep = new Cep();

		if ( cursor.moveToFirst() ) {
			
			cep.setId(cursor.getInt(_id));
			cep.setCodigo(cursor.getInt(_codigo));
			cep.setIndicadorNovo(cursor.getInt(_indicadorNovo));
			cep.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido));
			cep.setCodigoUnico(cursor.getString(_codigoUnico));
		}

		cursor.close();
		return cep;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "CEP";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getCodigo());
	}
}