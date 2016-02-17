package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto FoneTipo
 * </p>
 * 
 * @author Anderson Cabral
 * @since  10/12/2012
 */
public class FoneTipo extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int FNET_ID_INDEX         = 1;
    private static final int FNET_DSFONETIPO_INDEX = 2;
    
    public static final int RESIDENCIAL = 1;
	public static final int COMERCIAL = 2;
	public static final int CELULAR = 3;
	public static final int FAX = 4;

    private String descricao;
    
    public static final String[] columns = new String[]{
    	FoneTipoColunas.ID,
    	FoneTipoColunas.DESCRICAO
    };
    
    public static final class FoneTipoColunas implements BaseColumns{
    	public static final String ID 		 = "FNET_ID";
    	public static final String DESCRICAO = "FNET_DSFONETIPO";
    }
	
    public final class FoneTipoColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static FoneTipo inserirDoArquivo(List<String> c) {
    	
    	FoneTipo foneTipo = new FoneTipo();

    	foneTipo.setId(c.get(FNET_ID_INDEX));
    	foneTipo.setDescricao(c.get(FNET_DSFONETIPO_INDEX));
        
        return foneTipo;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(FoneTipoColunas.ID, getId());
		values.put(FoneTipoColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<FoneTipo> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<FoneTipo> listaFoneTipo = new ArrayList<FoneTipo>();
		if ( cursor.moveToFirst() ) {
			do{
				FoneTipo foneTipo = new FoneTipo();
				int codigo 	  = cursor.getColumnIndex(FoneTipoColunas.ID);
				int descricao = cursor.getColumnIndex(FoneTipoColunas.DESCRICAO);
				
				foneTipo.setId(cursor.getInt(codigo));
				foneTipo.setDescricao(cursor.getString(descricao));
				
				listaFoneTipo.add(foneTipo);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaFoneTipo;
	}
	
	public FoneTipo carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(FoneTipoColunas.ID);
		int descricao = cursor.getColumnIndex(FoneTipoColunas.DESCRICAO);
		
		FoneTipo foneTipo = new FoneTipo();

		if ( cursor.moveToFirst() ) {
			
			foneTipo.setId(cursor.getInt(codigo));
			foneTipo.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return foneTipo;
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
		return "FONE_TIPO";
	}
}
