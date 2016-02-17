package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto HidrometroMarca
 * </p>
 * 
 * @author Anderson Cabral
 * @since  03/01/2013
 */
public class HidrometroMarca extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int HIMC_ID_INDEX				  = 1;
    private static final int HIMC_CDHIDROMETROMARCA_INDEX = 2;

    private String codigo;
    
    public static final String[] columns = new String[]{
    	HidrometroMarcaColunas.ID,
    	HidrometroMarcaColunas.CODIGO
    };
    
    public static final class HidrometroMarcaColunas implements BaseColumns{
    	public static final String ID 		 = "HIMC_ID";
    	public static final String CODIGO = "HIMC_CDHIDROMETROMARCA";
    }
	
    public final class HidrometroMarcaColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String CODIGO = " CHAR NOT NULL";
		
		private String[] tipos = new String[] {ID, CODIGO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static HidrometroMarca inserirDoArquivo(List<String> c) {
    	
    	HidrometroMarca hidrometroMarca = new HidrometroMarca();

    	hidrometroMarca.setId(c.get(HIMC_ID_INDEX));
    	hidrometroMarca.setCodigo(c.get(HIMC_CDHIDROMETROMARCA_INDEX));
        
        return hidrometroMarca;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(HidrometroMarcaColunas.ID, getId());
		values.put(HidrometroMarcaColunas.CODIGO, getCodigo());

		return values;
	}
    
	public ArrayList<HidrometroMarca> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<HidrometroMarca> listaHidrometroMarca = new ArrayList<HidrometroMarca>();
		if ( cursor.moveToFirst() ) {
			do{
				HidrometroMarca hidrometroMarca = new HidrometroMarca();
				int id 	   = cursor.getColumnIndex(HidrometroMarcaColunas.ID);
				int codigo = cursor.getColumnIndex(HidrometroMarcaColunas.CODIGO);
				
				hidrometroMarca.setId(cursor.getInt(id));
				hidrometroMarca.setCodigo(cursor.getString(codigo));
				
				listaHidrometroMarca.add(hidrometroMarca);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroMarca;
	}
	
	public HidrometroMarca carregarEntidade(Cursor cursor) {		
		
		int id 	   = cursor.getColumnIndex(HidrometroMarcaColunas.ID);
		int codigo = cursor.getColumnIndex(HidrometroMarcaColunas.CODIGO);
		
		HidrometroMarca hidrometroMarca = new HidrometroMarca();

		if ( cursor.moveToFirst() ) {
			
			hidrometroMarca.setId(cursor.getInt(id));
			hidrometroMarca.setCodigo(cursor.getString(codigo));
		}

		cursor.close();
		return hidrometroMarca;
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
		return "HIDROMETRO_MARCA";
	}
}
