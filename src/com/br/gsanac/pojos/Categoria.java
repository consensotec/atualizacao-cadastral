package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 03/09/2014
 */
public class Categoria extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String descricao;
	
	// Index de acesso
    private static final int CATG_ID_INDEX = 1;
    private static final int CATG_DSCATEGORIA_INDEX = 2;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class CategoriaColuna implements BaseColumns{
    	public static final String ID = "CATG_ID";
    	public static final String DESCRICAO = "CATG_DSCATEGORIA";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	CategoriaColuna.ID,
    	CategoriaColuna.DESCRICAO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
	
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class CategoriaColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(16) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
  
    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
    /*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static Categoria converteLinhaArquivoEmObjeto(List<String> c) {
    	Categoria categoria = new Categoria();
    	categoria.setId(c.get(CATG_ID_INDEX));
    	categoria.setDescricao(c.get(CATG_DSCATEGORIA_INDEX));
        return categoria;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(CategoriaColuna.ID, getId());
		values.put(CategoriaColuna.DESCRICAO, getDescricao());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<Categoria> carregarListaEntidade(Cursor cursor) {		
		ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();
		if ( cursor.moveToFirst() ) {
			do{
				Categoria categoria = new Categoria();
				int _id = cursor.getColumnIndex(CategoriaColuna.ID);
				int _descricao = cursor.getColumnIndex(CategoriaColuna.DESCRICAO);
				
				categoria.setId(cursor.getInt(_id));
				categoria.setDescricao(cursor.getString(_descricao));
				listaCategoria.add(categoria);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaCategoria;
	}
	
	// Método converte um cursor em  objeto
	public Categoria carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(CategoriaColuna.ID);
		int _descricao = cursor.getColumnIndex(CategoriaColuna.DESCRICAO);
		
		Categoria categoria = new Categoria();

		if ( cursor.moveToFirst() ) {
			
			categoria.setId(cursor.getInt(_id));
			categoria.setDescricao(cursor.getString(_descricao));
		}
		
		cursor.close();
		return categoria;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "CATEGORIA";
	}

	@Override
	public String toString() {
		return descricao;
	}
}
