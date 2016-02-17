package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsável pelo objeto PavimentoRua
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class PavimentoRua extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     PRUA_ID_INDEX        = 1;
    private static final int     PRUA_DESCRICAO_INDEX        = 2;


    private String descricao;

    public static final String[] colunas              = new String[] {
    	PavimentoRuas.ID,
    	PavimentoRuas.DESCRICAO
    	};

    public static final class PavimentoRuas implements BaseColumns {
        public static final String ID = "PRUA_ID";
        public static final String DESCRICAO = "PRUA_DSPAVIMENTORUA";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "PAVIMENTO_RUA";
	}
    
    public final class PavimentoRuaTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(25) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static PavimentoRua inserirDoArquivo(List<String> c) {
    	
    	PavimentoRua pavimentoRua = new PavimentoRua();

    	pavimentoRua.setId(c.get(PRUA_ID_INDEX));
    	pavimentoRua.setDescricao(c.get(PRUA_DESCRICAO_INDEX));
        
        return pavimentoRua;
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
  
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(PavimentoRuas.ID, getId());
		values.put(PavimentoRuas.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<PavimentoRua> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<PavimentoRua> pavimentoRuas = new ArrayList<PavimentoRua>();
		
		do{
			int codigo = cursor.getColumnIndex(PavimentoRuas.ID);
			int descricao = cursor.getColumnIndex(PavimentoRuas.DESCRICAO);
			
			PavimentoRua pavimentoRua = new PavimentoRua();
			
			pavimentoRua.setId(cursor.getInt(codigo));
			pavimentoRua.setDescricao(cursor.getString(descricao));
			
			pavimentoRuas.add(pavimentoRua);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return pavimentoRuas;
	}
	
	public PavimentoRua carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(PavimentoRuas.ID);
		int descricao = cursor.getColumnIndex(PavimentoRuas.DESCRICAO);
		
		PavimentoRua pavimentoRua = new PavimentoRua();

		if ( cursor.moveToFirst() ) {
			
			pavimentoRua.setId(cursor.getInt(codigo));
			pavimentoRua.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return pavimentoRua;
	}
}