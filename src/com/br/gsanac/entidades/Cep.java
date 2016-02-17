package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import com.br.gsanac.util.ConstantesSistema;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * Classe responsavel pelo objeto Cep
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class Cep extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     CEP_ID_INDEX     = 1;
    private static final int     CEP_CODIGO_INDEX = 2;


    private Integer codigo;
    private Integer indicadorNovo;
    private Integer indicadorTransmitido;
    private String codigoUnico;

    public static final String[] colunas = new String[] {
    	Ceps.ID,
    	Ceps.CODIGO,
    	Ceps.INDICADORNOVO,
    	Ceps.INDICADOR_TRANSMITIDO,
    	Ceps.CODIGO_UNICO
    	
    };

    public static final class Ceps implements BaseColumns {
        public static final String ID = "CEP_ID";
        public static final String CODIGO = "CEP_CDCEP";
        public static final String INDICADORNOVO = "CEP_ICNOVO";
        public static final String INDICADOR_TRANSMITIDO = "CEP_ICTRANSMITIDO";
        public static final String CODIGO_UNICO = "CEP_CDUNIDO";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "CEP";
	}
    
    public final class CepTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String CODIGO = " INTEGER NOT NULL";
		public final String INDICADORNOVO = " INTEGER NOT NULL";
		public final String INDICADOR_TRANSMITIDO = " INTEGER NOT NULL";
		public final String CODIGO_UNICO = " VARCHAR(20) NULL ";
		
		private String[] tipos = new String[] {ID, CODIGO, INDICADORNOVO, INDICADOR_TRANSMITIDO, CODIGO_UNICO };	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static Cep inserirDoArquivo(List<String> c) {
    	
    	Cep cep = new Cep();

    	cep.setId(c.get(CEP_ID_INDEX));
    	cep.setCodigo(Integer.valueOf(c.get(CEP_CODIGO_INDEX)));
    	cep.setIndicadorNovo(ConstantesSistema.NAO);
    	cep.setIndicadorTransmitido(ConstantesSistema.NAO);

    	return cep;
    }
    
    public static Cep inserirAtualizarDoArquivoDividido(List<String> c) {
    	
    	Cep cep = new Cep();

//    	cep.setId();
    	cep.setCodigo(Integer.valueOf(c.get(CEP_CODIGO_INDEX)));
    	cep.setCodigoUnico(c.get(CEP_ID_INDEX));
    	cep.setIndicadorNovo(ConstantesSistema.SIM);
    	cep.setIndicadorTransmitido(ConstantesSistema.NAO);

    	return cep;
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

	public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(Ceps.ID, getId());
		values.put(Ceps.CODIGO, getCodigo());
		values.put(Ceps.INDICADORNOVO, getIndicadorNovo());
		values.put(Ceps.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());
		values.put(Ceps.CODIGO_UNICO, getCodigoUnico());

		return values;
	}
	
	public ArrayList<Cep> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<Cep> ceps = new ArrayList<Cep>();
		
		if ( cursor.moveToFirst() ) {
			do{
				Cep cep = new Cep();
				int id = cursor.getColumnIndex(Ceps.ID);
				int codigo = cursor.getColumnIndex(Ceps.CODIGO);
				int indicadorNovo = cursor.getColumnIndex(Ceps.INDICADORNOVO);
				int indicadorTransmitido = cursor.getColumnIndex(Ceps.INDICADOR_TRANSMITIDO);
				int codigoUnico = cursor.getColumnIndex(Ceps.CODIGO_UNICO);
				
				cep.setId(cursor.getInt(id));
				cep.setCodigo(cursor.getInt(codigo));
				cep.setIndicadorNovo(cursor.getInt(indicadorNovo));
				cep.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido));
				cep.setCodigoUnico(cursor.getString(codigoUnico));
				
				ceps.add(cep);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return ceps;
	}
	
	public Cep carregarEntidade(Cursor cursor) {		
		
		int id = cursor.getColumnIndex(Ceps.ID);
		int codigo = cursor.getColumnIndex(Ceps.CODIGO);
		int indicadorNovo = cursor.getColumnIndex(Ceps.INDICADORNOVO);
		int indicadorTransmitido = cursor.getColumnIndex(Ceps.INDICADOR_TRANSMITIDO);
		int codigoUnico = cursor.getColumnIndex(Ceps.CODIGO_UNICO);
		
		Cep cep = new Cep();

		if ( cursor.moveToFirst() ) {
			
			cep.setId(cursor.getInt(id));
			cep.setCodigo(cursor.getInt(codigo));
			cep.setIndicadorNovo(cursor.getInt(indicadorNovo));
			cep.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido));
			cep.setCodigoUnico(cursor.getString(codigoUnico));
		}

		cursor.close();
		return cep;
	}
}