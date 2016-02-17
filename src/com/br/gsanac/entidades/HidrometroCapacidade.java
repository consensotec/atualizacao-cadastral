package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto HidrometroCapacidade
 * </p>
 * 
 * @author Anderson Cabral
 * @since  03/01/2013
 */
public class HidrometroCapacidade extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int HICP_ID_INDEX         			   = 1;
    private static final int HICP_CDHIDROMETROCAPACIDADE_INDEX = 2;

    private String codigo;
    
    public static final String[] columns = new String[]{
    	HidrometroCapacidadeColunas.ID,
    	HidrometroCapacidadeColunas.CODIGO
    };
    
    public static final class HidrometroCapacidadeColunas implements BaseColumns{
    	public static final String ID 		 = "HICP_ID";
    	public static final String CODIGO = "HICP_CDHIDROMETROCAPACIDADE";
    }
	
    public final class HidrometroCapacidadeColunasTipo {
		public final String ID 	   = " INTEGER PRIMARY KEY";
		public final String CODIGO = " CHAR NOT NULL";
		
		private String[] tipos = new String[] {ID, CODIGO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static HidrometroCapacidade inserirDoArquivo(List<String> c) {
    	
    	HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();

    	hidrometroCapacidade.setId(c.get(HICP_ID_INDEX));
    	hidrometroCapacidade.setCodigo(c.get(HICP_CDHIDROMETROCAPACIDADE_INDEX));
        
        return hidrometroCapacidade;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(HidrometroCapacidadeColunas.ID, getId());
		values.put(HidrometroCapacidadeColunas.CODIGO, getCodigo());

		return values;
	}
    
	public ArrayList<HidrometroCapacidade> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<HidrometroCapacidade> listaHidrometroCapacidade = new ArrayList<HidrometroCapacidade>();
		if ( cursor.moveToFirst() ) {
			do{
				HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
				int id 	   = cursor.getColumnIndex(HidrometroCapacidadeColunas.ID);
				int codigo = cursor.getColumnIndex(HidrometroCapacidadeColunas.CODIGO);
				
				hidrometroCapacidade.setId(cursor.getInt(id));
				hidrometroCapacidade.setCodigo(cursor.getString(codigo));
				
				listaHidrometroCapacidade.add(hidrometroCapacidade);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroCapacidade;
	}
	
	public HidrometroCapacidade carregarEntidade(Cursor cursor) {		
		
		int id 	  = cursor.getColumnIndex(HidrometroCapacidadeColunas.ID);
		int codigo = cursor.getColumnIndex(HidrometroCapacidadeColunas.CODIGO);
		
		HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();

		if ( cursor.moveToFirst() ) {
			
			hidrometroCapacidade.setId(cursor.getInt(id));
			hidrometroCapacidade.setCodigo(cursor.getString(codigo));
		}

		cursor.close();
		return hidrometroCapacidade;
	}
    
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "HIDROMETRO_CAPACIDADE";
	}
}
