package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto HidrometroLocalInst
 * </p>
 * 
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class HidrometroLocalInst extends EntidadeBase {
	
	private static final long serialVersionUID = 1L;
	private static final int HILI_ID_INDEX         			   = 1;
    private static final int HILI_DSHIDMTLOCALINSTALACAO_INDEX = 2;
    
    private String descricao;
    
    public static final String[] columns = new String[]{
    	HidrometroLocalInstColunas.ID,
    	HidrometroLocalInstColunas.DESCRICAO
    };
    
    public static final class HidrometroLocalInstColunas implements BaseColumns{
    	public static final String ID 		 = "HILI_ID";
    	public static final String DESCRICAO = "HILI_DSHIDMTLOCALINSTALACAO";
    }
    
    public final class HidrometroLocalInstColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public static HidrometroLocalInst inserirDoArquivo(List<String> c) {
    	
    	HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();

    	hidrometroLocalInst.setId(c.get(HILI_ID_INDEX));
    	hidrometroLocalInst.setDescricao(c.get(HILI_DSHIDMTLOCALINSTALACAO_INDEX));
        
        return hidrometroLocalInst;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(HidrometroLocalInstColunas.ID, getId());
		values.put(HidrometroLocalInstColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<HidrometroLocalInst> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<HidrometroLocalInst> listaHidrometroLocalInst = new ArrayList<HidrometroLocalInst>();
		
		if ( cursor.moveToFirst() ) {
			do{
				HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
				int codigo 	  = cursor.getColumnIndex(HidrometroLocalInstColunas.ID);
				int descricao = cursor.getColumnIndex(HidrometroLocalInstColunas.DESCRICAO);
				
				hidrometroLocalInst.setId(cursor.getInt(codigo));
				hidrometroLocalInst.setDescricao(cursor.getString(descricao));
				
				listaHidrometroLocalInst.add(hidrometroLocalInst);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroLocalInst;
	}
    
	public HidrometroLocalInst carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(HidrometroLocalInstColunas.ID);
		int descricao = cursor.getColumnIndex(HidrometroLocalInstColunas.DESCRICAO);
		
		HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();

		if ( cursor.moveToFirst() ) {
			
			hidrometroLocalInst.setId(cursor.getInt(codigo));
			hidrometroLocalInst.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return hidrometroLocalInst;
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
		return "HIDROMETRO_LOCAL_INST";
	}
}
