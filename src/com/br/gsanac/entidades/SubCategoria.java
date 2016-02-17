package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto SubCategoria
 * </p>
 * 
 * @author Anderson Cabral
 * @since  10/12/2012
 */
public class SubCategoria extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int SCAT_ID_INDEX         	   = 1;
    private static final int CATG_ID_INDEX         	   = 2;
    private static final int SCAT_DSSUBCATEGORIA_INDEX = 3;

    private String descricao;
    private Categoria categoria;
    
    public static final String[] columns = new String[]{
    	SubCategoriaColunas.ID,
    	SubCategoriaColunas.CATEGORIA_ID,
    	SubCategoriaColunas.DESCRICAO
    };
    
    public static final class SubCategoriaColunas implements BaseColumns{
    	public static final String ID 		 	= "SCAT_ID";
    	public static final String CATEGORIA_ID = "CATG_ID";
    	public static final String DESCRICAO 	= "SCAT_DSSUBCATEGORIA";
    }
	
    public final class SubCategoriaColunasTipo {
		public final String ID 		  	 = " INTEGER PRIMARY KEY";
		public final String CATEGORIA_ID = " INTEGER NOT NULL";
		public final String DESCRICAO 	 = " VARCHAR(16) NOT NULL";
		
		private String[] tipos = new String[] {ID, CATEGORIA_ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static SubCategoria inserirDoArquivo(List<String> c) {
    	
    	SubCategoria subCategoria = new SubCategoria();
    	Categoria _categoria	  = new Categoria();
    	
    	subCategoria.setId(c.get(SCAT_ID_INDEX));
    	_categoria.setId(c.get(CATG_ID_INDEX));
    	subCategoria.setCategoria(_categoria);
    	subCategoria.setDescricao(c.get(SCAT_DSSUBCATEGORIA_INDEX));
        
        return subCategoria;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(SubCategoriaColunas.ID, getId());
		values.put(SubCategoriaColunas.CATEGORIA_ID, getCategoria().getId());
		values.put(SubCategoriaColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<SubCategoria> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<SubCategoria> listaSubCategoria = new ArrayList<SubCategoria>();
		cursor.moveToFirst();
		
		do{
			int codigo 	  	= cursor.getColumnIndex(SubCategoriaColunas.ID);
			int categoriaId = cursor.getColumnIndex(SubCategoriaColunas.CATEGORIA_ID);
			int descricao 	= cursor.getColumnIndex(SubCategoriaColunas.DESCRICAO);
			
			SubCategoria subCategoria = new SubCategoria();
			
			Categoria _categoria	  = new Categoria();
			
			subCategoria.setId(cursor.getInt(codigo));
			
			_categoria.setId(cursor.getInt(categoriaId));
	    	subCategoria.setCategoria(_categoria);
			
			subCategoria.setDescricao(cursor.getString(descricao));
			
			listaSubCategoria.add(subCategoria);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaSubCategoria;
	}
	
	public SubCategoria carregarEntidade(Cursor cursor) {		
		
		int codigo 	  	= cursor.getColumnIndex(SubCategoriaColunas.ID);
		int categoriaId = cursor.getColumnIndex(SubCategoriaColunas.CATEGORIA_ID);
		int descricao 	= cursor.getColumnIndex(SubCategoriaColunas.DESCRICAO);
		
		SubCategoria subCategoria = new SubCategoria();

		if ( cursor.moveToFirst() ) {
			Categoria _categoria	  = new Categoria();
			
			subCategoria.setId(cursor.getInt(codigo));
			
			_categoria.setId(cursor.getInt(categoriaId));
	    	subCategoria.setCategoria(_categoria);
			
			subCategoria.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return subCategoria;
	}
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "SUBCATEGORIA";
	}
}
