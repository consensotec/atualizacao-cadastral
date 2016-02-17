package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto Quadra
 * </p>
 * 
 * @author Anderson Cabral
 * @since  12/12/2012
 */
public class Quadra extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int QDRA_ID_INDEX       = 1;
    private static final int QDRA_NNQUADRA_INDEX = 2;
    private static final int STCM_CDSETORCOMERCIAL_INDEX = 3;

    private Integer numeroQuadra;
    private Integer ordenacao;
    private Integer codigoSetorComercial;
    
    public static final String[] columns = new String[]{
    	QuadraColunas.ID,
    	QuadraColunas.NUMQUADRA,
    	QuadraColunas.ORDENACAO,
    	QuadraColunas.CDSETORCOMERCIAL
    };
    
    public static final class QuadraColunas implements BaseColumns{
    	public static final String ID 		 = "QDRA_ID";
    	public static final String NUMQUADRA = "QDRA_NNQUADRA";
    	public static final String ORDENACAO = "QDRA_ORDENACAO";
    	public static final String CDSETORCOMERCIAL = "STCM_CDSETORCOMERCIAL";
    }
	
    public final class QuadraColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String NUMQUADRA = " INTEGER NOT NULL";
		public final String ORDENACAO = " INTEGER ";
		public final String CDSETORCOMERCIAL = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, NUMQUADRA, ORDENACAO, CDSETORCOMERCIAL};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public static Quadra inserirDoArquivo(List<String> c) {
    	
    	Quadra quadra = new Quadra();

    	quadra.setId(c.get(QDRA_ID_INDEX));
    	quadra.setNumeroQuadra(Integer.valueOf(c.get(QDRA_NNQUADRA_INDEX)));
    	quadra.setOrdenacao(quadra.getNumeroQuadra());
    	quadra.setCodigoSetorComercial(Integer.valueOf(c.get(STCM_CDSETORCOMERCIAL_INDEX)));
    	
        return quadra;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(QuadraColunas.ID, getId());
		values.put(QuadraColunas.NUMQUADRA, getNumeroQuadra());
		values.put(QuadraColunas.ORDENACAO, getOrdenacao());
		values.put(QuadraColunas.CDSETORCOMERCIAL, getCodigoSetorComercial());
		
		return values;
	}
    
	public ArrayList<Quadra> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<Quadra> listaQuadra = new ArrayList<Quadra>();
		cursor.moveToFirst();
		
		do{
			int codigo 	  = cursor.getColumnIndex(QuadraColunas.ID);
			int numeroQuadra = cursor.getColumnIndex(QuadraColunas.NUMQUADRA);
			int ordenacao = cursor.getColumnIndex(QuadraColunas.ORDENACAO);
			int codigoSetorComercial = cursor.getColumnIndex(QuadraColunas.CDSETORCOMERCIAL);
			
			Quadra quadra = new Quadra();
			
			quadra.setId(cursor.getInt(codigo));
			quadra.setNumeroQuadra(cursor.getInt(numeroQuadra));
			quadra.setOrdenacao(cursor.getInt(ordenacao));
			quadra.setCodigoSetorComercial(cursor.getInt(codigoSetorComercial));
			
			listaQuadra.add(quadra);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaQuadra;
	}
	
	public Quadra carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(QuadraColunas.ID);
		int numeroQuadra = cursor.getColumnIndex(QuadraColunas.NUMQUADRA);
		int ordenacao = cursor.getColumnIndex(QuadraColunas.ORDENACAO);
		int codigoSetorComercial = cursor.getColumnIndex(QuadraColunas.CDSETORCOMERCIAL);
		
		Quadra quadra = new Quadra();

		if ( cursor.moveToFirst() ) {
			
			quadra.setId(cursor.getInt(codigo));
			quadra.setNumeroQuadra(cursor.getInt(numeroQuadra));
			quadra.setOrdenacao(cursor.getInt(ordenacao));
			quadra.setCodigoSetorComercial(cursor.getInt(codigoSetorComercial));
		}

		cursor.close();
		return quadra;
	}
    
	public Integer getNumeroQuadra() {
		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra) {
		this.numeroQuadra = numeroQuadra;
	}
	
    public Integer getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(Integer ordenacao) {
		this.ordenacao = ordenacao;
	}

	public Integer getCodigoSetorComercial() {
		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial) {
		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "QUADRA";
	}
}
