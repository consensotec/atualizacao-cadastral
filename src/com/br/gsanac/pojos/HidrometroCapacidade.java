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
public class HidrometroCapacidade extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	//  Atributos
	private String codigo;
	
	// Index de acesso
    private static final int HICP_ID_INDEX = 1;
    private static final int HICP_CDHIDROMETROCAPACIDADE_INDEX = 2;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class HidrometroCapacidadeColuna implements BaseColumns{
    	public static final String ID = "HICP_ID";
    	public static final String CODIGO = "HICP_CDHIDROMETROCAPACIDADE";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	HidrometroCapacidadeColuna.ID,
    	HidrometroCapacidadeColuna.CODIGO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
 	
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class HidrometroCapacidadeColunasTipo {
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
    public static HidrometroCapacidade converteLinhaArquivoEmObjeto(List<String> c) {
    	HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
    	hidrometroCapacidade.setId(c.get(HICP_ID_INDEX));
    	hidrometroCapacidade.setCodigo(c.get(HICP_CDHIDROMETROCAPACIDADE_INDEX));
        return hidrometroCapacidade;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(HidrometroCapacidadeColuna.ID, getId());
		values.put(HidrometroCapacidadeColuna.CODIGO, getCodigo());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<HidrometroCapacidade> carregarListaEntidade(Cursor cursor) {		
		ArrayList<HidrometroCapacidade> listaHidrometroCapacidade = new ArrayList<HidrometroCapacidade>();
		
		if ( cursor.moveToFirst() ) {
			do{
				HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
				int _id = cursor.getColumnIndex(HidrometroCapacidadeColuna.ID);
				int _codigo = cursor.getColumnIndex(HidrometroCapacidadeColuna.CODIGO);
				
				hidrometroCapacidade.setId(cursor.getInt(_id));
				hidrometroCapacidade.setCodigo(cursor.getString(_codigo));
				
				listaHidrometroCapacidade.add(hidrometroCapacidade);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroCapacidade;
	}
	
	// Método converte um cursor em  objeto
	public HidrometroCapacidade carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(HidrometroCapacidadeColuna.ID);
		int _codigo = cursor.getColumnIndex(HidrometroCapacidadeColuna.CODIGO);
		
		HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();

		if ( cursor.moveToFirst() ) {
			
			hidrometroCapacidade.setId(cursor.getInt(_id));
			hidrometroCapacidade.setCodigo(cursor.getString(_codigo));
		}

		cursor.close();
		return hidrometroCapacidade;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "HIDROMETRO_CAPACIDADE";
	}
}
