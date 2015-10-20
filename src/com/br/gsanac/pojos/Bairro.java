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
public class Bairro extends EntidadeBasica {

    private static final long serialVersionUID = 4327285475536305741L;
    
    // Atributos
    private Integer codigo;
    private String descricao;
    
    // Index de acesso
    private static final int BAIR_ID_INDEX = 1;
    private static final int BAIR_CODIGO_INDEX = 2;
    private static final int BAIR_DESCRICAO_INDEX = 3;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class BairroColuna implements BaseColumns {
    	public static final String ID = "BAIR_ID";
    	public static final String CODIGO = "BAIR_CDBAIRRO";
    	public static final String DESCRICAO = "BAIR_NMBAIRRO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[] {
    	BairroColuna.ID,
    	BairroColuna.CODIGO,
    	BairroColuna.DESCRICAO,
    	};
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class BairroColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String CODIGO = " INTEGER NOT NULL";
		private final String DESCRICAO = " VARCHAR(30) NOT NULL";
		
		private String[] tipos = new String[] {ID, CODIGO, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
    public static Bairro converteLinhaArquivoEmObjeto(List<String> c) {
    	Bairro bairro = new Bairro();
    	bairro.setId(c.get(BAIR_ID_INDEX));
    	bairro.setCodigo(Integer.valueOf(c.get(BAIR_CODIGO_INDEX)));
    	bairro.setDescricao(c.get(BAIR_DESCRICAO_INDEX));
        return bairro;
    }

    // Método retorna ContentValues
	public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(BairroColuna.ID, getId());
		values.put(BairroColuna.CODIGO, getCodigo());
		values.put(BairroColuna.DESCRICAO, getDescricao());
		return values;
	}
	
	/*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<Bairro> carregarListaEntidade(Cursor cursor) {		
		ArrayList<Bairro> bairros = new ArrayList<Bairro>();
		if ( cursor.moveToFirst() ) {
			do{
				Bairro bairro = new Bairro();
			
				int _id = cursor.getColumnIndex(BairroColuna.ID);
				int _codigo = cursor.getColumnIndex(BairroColuna.CODIGO);
				int _descricao = cursor.getColumnIndex(BairroColuna.DESCRICAO);
				
				bairro.setId(cursor.getInt(_id));
				bairro.setCodigo(cursor.getInt(_codigo));
				bairro.setDescricao(cursor.getString(_descricao));
				bairros.add(bairro);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return bairros;
	}
	
	// Método converte um cursor em  objeto
	public Bairro carregarEntidade(Cursor cursor) {		
		int _id = cursor.getColumnIndex(BairroColuna.ID);
		int _codigo = cursor.getColumnIndex(BairroColuna.CODIGO);
		int _descricao = cursor.getColumnIndex(BairroColuna.DESCRICAO);
		
		Bairro bairro = new Bairro();

		if ( cursor.moveToFirst() ) {
			bairro.setId(cursor.getInt(_id));
			bairro.setCodigo(cursor.getInt(_codigo));
			bairro.setDescricao(cursor.getString(_descricao));
		}
		cursor.close();
		return bairro;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "BAIRRO";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDescricao();
	}
}