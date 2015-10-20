package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 04/09/2014
 */
public class HidrometroMarca extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
    private String codigo;
    
    // Index de acesso
    private static final int HIMC_ID_INDEX = 1;
    private static final int HIMC_CDHIDROMETROMARCA_INDEX = 2;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class HidrometroMarcaColuna implements BaseColumns{
    	public static final String ID = "HIMC_ID";
    	public static final String CODIGO = "HIMC_CDHIDROMETROMARCA";
    }
	
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	HidrometroMarcaColuna.ID,
    	HidrometroMarcaColuna.CODIGO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class HidrometroMarcaColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String CODIGO = " CHAR NOT NULL";
		
		private String[] tipos = new String[] {ID, CODIGO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
    
	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static HidrometroMarca converteLinhaArquivoEmObjeto(List<String> c) {
    	HidrometroMarca hidrometroMarca = new HidrometroMarca();
    	hidrometroMarca.setId(c.get(HIMC_ID_INDEX));
    	hidrometroMarca.setCodigo(c.get(HIMC_CDHIDROMETROMARCA_INDEX));
        return hidrometroMarca;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(HidrometroMarcaColuna.ID, getId());
		values.put(HidrometroMarcaColuna.CODIGO, getCodigo());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<HidrometroMarca> carregarListaEntidade(Cursor cursor) {		
		ArrayList<HidrometroMarca> listaHidrometroMarca = new ArrayList<HidrometroMarca>();
		
		if ( cursor.moveToFirst() ) {
			do{
				HidrometroMarca hidrometroMarca = new HidrometroMarca();
				int _id 	   = cursor.getColumnIndex(HidrometroMarcaColuna.ID);
				int _codigo = cursor.getColumnIndex(HidrometroMarcaColuna.CODIGO);
				
				hidrometroMarca.setId(cursor.getInt(_id));
				hidrometroMarca.setCodigo(cursor.getString(_codigo));
				
				listaHidrometroMarca.add(hidrometroMarca);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroMarca;
	}
	
	// Método converte um cursor em  objeto
	public HidrometroMarca carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(HidrometroMarcaColuna.ID);
		int _codigo = cursor.getColumnIndex(HidrometroMarcaColuna.CODIGO);
		
		HidrometroMarca hidrometroMarca = new HidrometroMarca();

		if ( cursor.moveToFirst() ) {
			
			hidrometroMarca.setId(cursor.getInt(_id));
			hidrometroMarca.setCodigo(cursor.getString(_codigo));
		}

		cursor.close();
		return hidrometroMarca;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "HIDROMETRO_MARCA";
	}
}
