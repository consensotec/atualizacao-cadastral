package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto Categoria
 * </p>
 * 
 * @author Anderson Cabral
 * @since  10/12/2012
 */
public class Categoria extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int CATG_ID_INDEX          = 1;
    private static final int CATG_DSCATEGORIA_INDEX = 2;

    private String descricao;
    
    public static final String[] columns = new String[]{
    	CategoriaColunas.ID,
    	CategoriaColunas.DESCRICAO
    };
    
    public static final class CategoriaColunas implements BaseColumns{
    	public static final String ID 		 = "CATG_ID";
    	public static final String DESCRICAO = "CATG_DSCATEGORIA";
    }
	
    public final class CategoriaColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(16) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static Categoria inserirDoArquivo(List<String> c) {
    	
    	Categoria categoria = new Categoria();

    	categoria.setId(c.get(CATG_ID_INDEX));
    	categoria.setDescricao(c.get(CATG_DSCATEGORIA_INDEX));
        
        return categoria;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(CategoriaColunas.ID, getId());
		values.put(CategoriaColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<Categoria> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();
		if ( cursor.moveToFirst() ) {
			do{
				Categoria categoria = new Categoria();
				int codigo 	  = cursor.getColumnIndex(CategoriaColunas.ID);
				int descricao = cursor.getColumnIndex(CategoriaColunas.DESCRICAO);
				
				categoria.setId(cursor.getInt(codigo));
				categoria.setDescricao(cursor.getString(descricao));
				listaCategoria.add(categoria);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaCategoria;
	}
	
	public Categoria carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(CategoriaColunas.ID);
		int descricao = cursor.getColumnIndex(CategoriaColunas.DESCRICAO);
		
		Categoria categoria = new Categoria();

		if ( cursor.moveToFirst() ) {
			
			categoria.setId(cursor.getInt(codigo));
			categoria.setDescricao(cursor.getString(descricao));
		}
		
		cursor.close();
		return categoria;
	}
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "CATEGORIA";
	}
}
