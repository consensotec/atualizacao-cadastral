package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto MedicaoTipo
 * </p>
 * 
 * @author Anderson Cabral
 * @since  02/01/2013
 */
public class MedicaoTipo extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int MEDT_ID_INDEX         	  = 1;
    private static final int MEDT_DSMEDICAOTIPO_INDEX = 2;

    private String descricao;
    
    public static final String[] columns = new String[]{
    	MedicaoTipoColunas.ID,
    	MedicaoTipoColunas.DESCRICAO
    };
    
    public static final class MedicaoTipoColunas implements BaseColumns{
    	public static final String ID 		 = "MEDT_ID";
    	public static final String DESCRICAO = "MEDT_DSMEDICAOTIPO";
    }
	
    public final class MedicaoTipoColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static MedicaoTipo inserirDoArquivo(List<String> c) {
    	
    	MedicaoTipo medicaoTipo = new MedicaoTipo();

    	medicaoTipo.setId(c.get(MEDT_ID_INDEX));
    	medicaoTipo.setDescricao(c.get(MEDT_DSMEDICAOTIPO_INDEX));
        
        return medicaoTipo;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(MedicaoTipoColunas.ID, getId());
		values.put(MedicaoTipoColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<MedicaoTipo> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<MedicaoTipo> listaMedicaoTipo = new ArrayList<MedicaoTipo>();
		if ( cursor.moveToFirst() ) {
			do{
				MedicaoTipo medicaoTipo = new MedicaoTipo();
				int codigo 	  = cursor.getColumnIndex(MedicaoTipoColunas.ID);
				int descricao = cursor.getColumnIndex(MedicaoTipoColunas.DESCRICAO);
				
				medicaoTipo.setId(cursor.getInt(codigo));
				medicaoTipo.setDescricao(cursor.getString(descricao));
				
				listaMedicaoTipo.add(medicaoTipo);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaMedicaoTipo;
	}
	
	public MedicaoTipo carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(MedicaoTipoColunas.ID);
		int descricao = cursor.getColumnIndex(MedicaoTipoColunas.DESCRICAO);
		
		MedicaoTipo medicaoTipo = new MedicaoTipo();

		if ( cursor.moveToFirst() ) {
			
			medicaoTipo.setId(cursor.getInt(codigo));
			medicaoTipo.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return medicaoTipo;
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
		return "MEDICAO_TIPO";
	}
}
