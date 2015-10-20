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
public class HidrometroLocalInst extends EntidadeBasica {
	
	private static final long serialVersionUID = 1L;
	
	// Atributos
    private String descricao;
    
    // Index de acesso
    private static final int HILI_ID_INDEX = 1;
    private static final int HILI_DSHIDMTLOCALINSTALACAO_INDEX = 2;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class HidrometroLocalInstColuna implements BaseColumns{
    	public static final String ID = "HILI_ID";
    	public static final String DESCRICAO = "HILI_DSHIDMTLOCALINSTALACAO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	HidrometroLocalInstColuna.ID,
    	HidrometroLocalInstColuna.DESCRICAO
    };
    
	// Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class HidrometroLocalInstColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
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
    public static HidrometroLocalInst converteLinhaArquivoEmObjeto(List<String> c) {
    	HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
    	hidrometroLocalInst.setId(c.get(HILI_ID_INDEX));
    	hidrometroLocalInst.setDescricao(c.get(HILI_DSHIDMTLOCALINSTALACAO_INDEX));
        return hidrometroLocalInst;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(HidrometroLocalInstColuna.ID, getId());
		values.put(HidrometroLocalInstColuna.DESCRICAO, getDescricao());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<HidrometroLocalInst> carregarListaEntidade(Cursor cursor) {		
		ArrayList<HidrometroLocalInst> listaHidrometroLocalInst = new ArrayList<HidrometroLocalInst>();
		
		if ( cursor.moveToFirst() ) {
			do{
				HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
				int _id  = cursor.getColumnIndex(HidrometroLocalInstColuna.ID);
				int _descricao = cursor.getColumnIndex(HidrometroLocalInstColuna.DESCRICAO);
				
				hidrometroLocalInst.setId(cursor.getInt(_id));
				hidrometroLocalInst.setDescricao(cursor.getString(_descricao));
				
				listaHidrometroLocalInst.add(hidrometroLocalInst);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroLocalInst;
	}
    
	// Método converte um cursor em  objeto
	public HidrometroLocalInst carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(HidrometroLocalInstColuna.ID);
		int _descricao = cursor.getColumnIndex(HidrometroLocalInstColuna.DESCRICAO);
		
		HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();

		if ( cursor.moveToFirst() ) {
			
			hidrometroLocalInst.setId(cursor.getInt(_id));
			hidrometroLocalInst.setDescricao(cursor.getString(_descricao));
		}

		cursor.close();
		return hidrometroLocalInst;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "HIDROMETRO_LOCAL_INST";
	}
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return this.getDescricao();
    }
}
