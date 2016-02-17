package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsável pelo objeto PavimentoCalcada
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class PavimentoCalcada extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     PCAL_ID_INDEX        = 1;
    private static final int     PCAL_DESCRICAO_INDEX        = 2;


    private String descricao;

    public static final String[] colunas              = new String[] {
    	PavimentoCalcadas.ID,
    	PavimentoCalcadas.DESCRICAO
    	};

    public static final class PavimentoCalcadas implements BaseColumns {
        public static final String ID = "PRUA_ID";
        public static final String DESCRICAO = "PRUA_DSPAVIMENTORUA";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "PAVIMENTO_CALCADA";
	}
    
    public final class PavimentoCalcadaTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(25) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static PavimentoCalcada inserirDoArquivo(List<String> c) {
    	
    	PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();

    	pavimentoCalcada.setId(c.get(PCAL_ID_INDEX));
    	pavimentoCalcada.setDescricao(c.get(PCAL_DESCRICAO_INDEX));
        
        return pavimentoCalcada;
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
  
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(PavimentoCalcadas.ID, getId());
		values.put(PavimentoCalcadas.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<PavimentoCalcada> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<PavimentoCalcada> pavimentoCalcadas = new ArrayList<PavimentoCalcada>();
		cursor.moveToFirst();
		
		do{
			int codigo = cursor.getColumnIndex(PavimentoCalcadas.ID);
			int descricao = cursor.getColumnIndex(PavimentoCalcadas.DESCRICAO);
			
			PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
			
			pavimentoCalcada.setId(cursor.getInt(codigo));
			pavimentoCalcada.setDescricao(cursor.getString(descricao));
			
			pavimentoCalcadas.add(pavimentoCalcada);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return pavimentoCalcadas;
	}
	
	public PavimentoCalcada carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(PavimentoCalcadas.ID);
		int descricao = cursor.getColumnIndex(PavimentoCalcadas.DESCRICAO);
		
		PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();

		if ( cursor.moveToFirst() ) {
			
			pavimentoCalcada.setId(cursor.getInt(codigo));
			pavimentoCalcada.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return pavimentoCalcada;
	}
}