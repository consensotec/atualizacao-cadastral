package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import com.br.gsanac.util.Util;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto Municipio
 * </p>
 * 
 * @author Anderson Cabral
 * @since  12/12/2012
 */
public class Municipio extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int MUNI_ID_INDEX          = 1;
    private static final int MUNI_NMMUNICIPIO_INDEX = 2;
    private static final int MUNI_CDCEPUNICO_INDEX  = 3;

    private String nome;
    private Integer codigoCepUnico;
    
    public static final String[] columns = new String[]{
    	MunicipioColunas.ID,
    	MunicipioColunas.NOME,
    	MunicipioColunas.CEPUNICO
    };
    
    public static final class MunicipioColunas implements BaseColumns{
    	public static final String ID 		= "MUNI_ID";
    	public static final String NOME 	= "MUNI_NMMUNICIPIO";
    	public static final String CEPUNICO = "MUNI_CDCEPUNICO";
    }
	
    public final class MunicipioColunasTipo {
		public final String ID 	 	 = " INTEGER PRIMARY KEY";
		public final String NOME 	 = " VARCHAR(30) NOT NULL";
		public final String CEPUNICO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, NOME, CEPUNICO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static Municipio inserirDoArquivo(List<String> c) {
    	
    	Municipio municipio = new Municipio();

    	municipio.setId(c.get(MUNI_ID_INDEX));
    	municipio.setNome(c.get(MUNI_NMMUNICIPIO_INDEX));
    	municipio.setCodigoCepUnico(Util.verificarNuloInt(c.get(MUNI_CDCEPUNICO_INDEX)));
        
        return municipio;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(MunicipioColunas.ID, getId());
		values.put(MunicipioColunas.NOME, getNome());
		values.put(MunicipioColunas.CEPUNICO, getCodigoCepUnico());

		return values;
	}
    
	public ArrayList<Municipio> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<Municipio> listaMunicipio = new ArrayList<Municipio>();
		cursor.moveToFirst();
		
		do{
			int _codigo   = cursor.getColumnIndex(MunicipioColunas.ID);
			int _nome 	  = cursor.getColumnIndex(MunicipioColunas.NOME);
			int _cepUnico = cursor.getColumnIndex(MunicipioColunas.CEPUNICO);
			
			Municipio municipio = new Municipio();
			
			municipio.setId(cursor.getInt(_codigo));
			municipio.setNome(cursor.getString(_nome));
			municipio.setCodigoCepUnico(cursor.getInt(_cepUnico));
			
			listaMunicipio.add(municipio);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaMunicipio;
	}
	
	public Municipio carregarEntidade(Cursor cursor) {		
		
		int _codigo   = cursor.getColumnIndex(MunicipioColunas.ID);
		int _nome 	  = cursor.getColumnIndex(MunicipioColunas.NOME);
		int _cepUnico = cursor.getColumnIndex(MunicipioColunas.CEPUNICO);
		
		Municipio municipio = new Municipio();

		if ( cursor.moveToFirst() ) {
			
			municipio.setId(cursor.getInt(_codigo));
			municipio.setNome(cursor.getString(_nome));
			municipio.setCodigoCepUnico(cursor.getInt(_cepUnico));
		}

		cursor.close();
		return municipio;
	}
	
    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getCodigoCepUnico() {
		return codigoCepUnico;
	}

	public void setCodigoCepUnico(Integer codigoCepUnico) {
		this.codigoCepUnico = codigoCepUnico;
	}

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "MUNICIPIO";
	}
}
