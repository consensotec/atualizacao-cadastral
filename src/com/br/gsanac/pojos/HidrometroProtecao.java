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
public class HidrometroProtecao extends EntidadeBasica{

	private static final long serialVersionUID = 1L;

	// Atributos
    private String descricao;
    
    // Index de acesso
    private static final int HIPR_ID_INDEX = 1;
    private static final int HIPR_DSHIDROMETROPROTECAO_INDEX = 2;
   
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class HidrometroProtecaoColuna implements BaseColumns{
    	public static final String ID = "HIPR_ID";
    	public static final String DESCRICAO = "HIPR_DSHIDROMETROPROTECAO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	HidrometroProtecaoColuna.ID,
    	HidrometroProtecaoColuna.DESCRICAO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class HidrometroProtecaoColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(50) NOT NULL";
		
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
    public static HidrometroProtecao converteLinhaArquivoEmObjeto(List<String> c) {
    	HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
    	hidrometroProtecao.setId(c.get(HIPR_ID_INDEX));
    	hidrometroProtecao.setDescricao(c.get(HIPR_DSHIDROMETROPROTECAO_INDEX));
        return hidrometroProtecao;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(HidrometroProtecaoColuna.ID, getId());
		values.put(HidrometroProtecaoColuna.DESCRICAO, getDescricao());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<HidrometroProtecao> carregarListaEntidade(Cursor cursor) {		
		ArrayList<HidrometroProtecao> listaHidrometroProtecao = new ArrayList<HidrometroProtecao>();
		
		if ( cursor.moveToFirst() ) {
			do{
				int _id 	  = cursor.getColumnIndex(HidrometroProtecaoColuna.ID);
				int _descricao = cursor.getColumnIndex(HidrometroProtecaoColuna.DESCRICAO);
				
				HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
	
				hidrometroProtecao.setId(cursor.getInt(_id));
				hidrometroProtecao.setDescricao(cursor.getString(_descricao));
			
				listaHidrometroProtecao.add(hidrometroProtecao);
				
			} while (cursor.moveToNext());
		}		
		
		cursor.close();
		return listaHidrometroProtecao;
	}
	
	// Método converte um cursor em  objeto
	public HidrometroProtecao carregarEntidade(Cursor cursor) {		
		
		int _id 	  = cursor.getColumnIndex(HidrometroProtecaoColuna.ID);
		int _descricao = cursor.getColumnIndex(HidrometroProtecaoColuna.DESCRICAO);
		
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();

		if ( cursor.moveToFirst() ) {
			
			hidrometroProtecao.setId(cursor.getInt(_id));
			hidrometroProtecao.setDescricao(cursor.getString(_descricao));
		}

		cursor.close();
		return hidrometroProtecao;
	}
    
	// Retorna no da tabela
    public String getNomeTabela(){
		return "HIDROMETRO_PROTECAO";
	}
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return this.getDescricao();
    }
}
